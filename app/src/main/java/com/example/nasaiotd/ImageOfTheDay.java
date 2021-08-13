package com.example.nasaiotd;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ImageOfTheDay extends AppCompatActivity {

    // Formatter for appending date to API URL.
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private NASAImageQuery req;
    SQLiteDatabase db;

    private final String apiLink = "https://api.nasa.gov/planetary/apod?api_key=VV3DcWE3AbEdOJQqg6ROHRbFU6p9dRrDlM4ngREj&date=";
    private String apiLinkDate;

    private String fileType;
    private String title;
    private String date;
    private String description;
    private String imageURL;
    private String imageHDURL;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_image_of_the_day);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        /** Toolbar and Navigation Bar Code */
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Navigation drawer similar to inclassexample code
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        /** Toolbar and Navigation Bar Code Ends */

        // Append current date to API URL and query.
        apiLinkDate = FORMATTER.format(Calendar.getInstance().getTime());
        req = new NASAImageQuery();
        req.execute(apiLink + apiLinkDate);

        TextView welcomeTextIotD = findViewById(R.id.welcomeTextIotD);
        String namePasser = getResources().getString(R.string.welcomeTextIotD, name);
        welcomeTextIotD.setText(namePasser);


        // Clicking on the image button will summon a toast with info pertaining to the image.
        ImageButton imageOfTheDay = findViewById(R.id.imageOfTheDay);
        imageOfTheDay.setOnClickListener( (click) -> {
            Toast.makeText(ImageOfTheDay.this, description, Toast.LENGTH_LONG).show();
        });

        // This button opens the image's HD URL on the device's default browser.
        Button hdButton = findViewById(R.id.hdButtonIotD);
        hdButton.setOnClickListener( (click) -> {
            Uri uri = Uri.parse(imageHDURL);
            Intent openBrowser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(openBrowser);
        });

        // This button opens a date picker allowing users to choose an image by date.
        Button dateButton = findViewById(R.id.dateButtonIotD);
        dateButton.setOnClickListener( (click) -> {

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(this, (datePickerL, chosenYear, chosenMonth, chosenDay) -> {

                // Get date from the date picker.
                Calendar c2 = Calendar.getInstance();
                c2.set(chosenYear, chosenMonth, chosenDay);

                // Format date to append to NASA API URL.
                apiLinkDate = FORMATTER.format(c2.getTime());

                // Run new query with chosen date.
                req = new NASAImageQuery();
                req.execute(apiLink + apiLinkDate);

                // Update welcome message
                // *************** translate **********
                welcomeTextIotD.setText("On this day...");

            }, year, month, day);
            datePicker.show();
        });

        // This button saves the current image, adding it to the list view.
        Button saveButton = findViewById(R.id.saveButtonIotD);
        saveButton.setOnClickListener( (click) -> {
            if (fileType.equals("image")) {
                // Add to the DB and get new ID
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(ImageDBOpener.COL_TITLE, title);
                newRowValues.put(ImageDBOpener.COL_DATE, date);
                newRowValues.put(ImageDBOpener.COL_DESCRIPTION, description);
                newRowValues.put(ImageDBOpener.COL_URL, imageURL);
                newRowValues.put(ImageDBOpener.COL_HDURL, imageHDURL);

                // Get DB connection and insert new values.
                ImageDBOpener imageDBOpener = new ImageDBOpener(this);
                db = imageDBOpener.getWritableDatabase();
                db.insert(ImageDBOpener.TABLE_NAME, null, newRowValues);

                // Verify that the image has been saved
                Toast.makeText(ImageOfTheDay.this, R.string.saveConfirm, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ImageOfTheDay.this, R.string.saveDeny, Toast.LENGTH_SHORT).show();
            }
        });

        ProgressBar downloadProgress = findViewById(R.id.progressBar);
        downloadProgress.setVisibility(View.VISIBLE);
    }

//    public boolean fileExists(String fname){
//        File file = getBaseContext().getFileStreamPath(fname);
//        return file.exists();
//    }

    private class NASAImageQuery extends AsyncTask<String, Integer, String> {

        Bitmap nasaImage;

        ProgressBar downloadProgress = findViewById(R.id.progressBar);

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream responseTwo = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(responseTwo, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                // convert string to JSON:
                JSONObject nasaImageData = new JSONObject(result);

                fileType = null;
                fileType = nasaImageData.getString("media_type");
                imageURL = nasaImageData.getString("url");
                date = nasaImageData.getString("date");

                // Handle non-images.
                if (fileType.equals("image")) {
                    // Pull API data.
                    title = nasaImageData.getString("title");
                    description = nasaImageData.getString("explanation");
                    imageHDURL = nasaImageData.getString("hdurl");

                    // get image
                    nasaImage = null;
                    url = new URL(imageURL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        nasaImage = BitmapFactory.decodeStream(urlConnection.getInputStream());
                    }
                    FileOutputStream outputStream = openFileOutput(imageURL, Context.MODE_PRIVATE);
                    nasaImage.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);
                } else {
                    title = "No image this time.";
                    description = "meow";
                    imageHDURL = "";
                    nasaImage = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.nasa_image);
                }
            } catch (Exception e) {
                Log.e("Error accessing API", e.getMessage());
            }
            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            downloadProgress.setProgress(value[0]);
        }

        @Override
        public void onPostExecute(String fromDoInBackground) {

            ImageButton imageOfTheDay = findViewById(R.id.imageOfTheDay);
            imageOfTheDay.setImageBitmap(nasaImage);

            TextView imageTitle = findViewById(R.id.imageTitle);
            imageTitle.setText(title);

            TextView dateIotD = findViewById(R.id.dateIotD);
            dateIotD.setText("(" + date + ")");

            downloadProgress.setVisibility(View.INVISIBLE);
        }
    }

    /** Toolbar Functions */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflating the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        //putting in case for every item in menu xml
        switch (item.getItemId()) {
            //what to do when each menu item is selected
            case R.id.choice1:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.choice2:
                intent = new Intent(this, ImageList.class);
                startActivity(intent);
                break;
            case R.id.choice3:
                intent = new Intent(this, ImageOfTheDay.class);
                startActivity(intent);
                break;
            case R.id.choice4:
                /** Alert Dialogue goes here for help (instructions) */
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Instructions for Image Of The Day Page")
                        .setMessage("This is the Image Of The Day Page. " +
                                "\n 1. Here you can see the current Image of the day based on the date." +
                                "\n 2. In order to see previous Image of the days, You can click SELECT DATE button to select a previous date. " +
                                "\n 3. To save an Image you like, when your Image is displayed, you can click SAVE IMAGE button. Your Image will be saved in Gallery." +
                                "\n 4. If you want to see the Image in HD then you can click VIEW IN HD button and it will open up browser to view the image.")
                        .setPositiveButton("Ok", (click, arg) -> {
                        })
                        .create().show(); //creating the dialogue
                break;
        }
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId())
        {
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.pictureList:
                intent = new Intent(this, ImageList.class);
                startActivity(intent);
                break;
            case R.id.iotd:
                intent = new Intent(this, ImageOfTheDay.class);
                startActivity(intent);
                //setResult(500);
                // finish();
            case R.id.help:
                /** Alert Dialogue goes here for help (instructions) */
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Instructions for Image Of The Day Page")
                        .setMessage("This is the Image Of The Day Page. " +
                                "\n 1. Here you can see the current Image of the day based on the date." +
                                "\n 2. In order to see previous Image of the days, You can click SELECT DATE button to select a previous date. " +
                                "\n 3. To save an Image you like, when your Image is displayed, you can click SAVE IMAGE button. Your Image will be saved in Gallery." +
                                "\n 4. If you want to see the Image in HD then you can click VIEW IN HD button and it will open up browser to view the image.")
                        .setPositiveButton("Ok", (click, arg) -> {
                        })
                        .create().show(); //creating the dialog
                break;
        }
        return false;
    }
    /** Toolbar Functions end */
}