package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    // This is our NASA API key: VV3DcWE3AbEdOJQqg6ROHRbFU6p9dRrDlM4ngREj

    // Formatter for appending date to API URL.
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private NASAImageQuery req;

    private String apiLink = "https://api.nasa.gov/planetary/apod?api_key=VV3DcWE3AbEdOJQqg6ROHRbFU6p9dRrDlM4ngREj&date=";
    private String apiLinkDate;
    private String toastMessage;
    private String hdButtonLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_the_day);

        // Append current date to API URL and query.
        apiLinkDate = FORMATTER.format(Calendar.getInstance().getTime());
        req = new NASAImageQuery();
        req.execute(apiLink + apiLinkDate);

        TextView welcomeTextIotD = findViewById(R.id.welcomeTextIotD);

        ImageButton imageOfTheDay = findViewById(R.id.imageOfTheDay);

        // Clicking on the image button will summon a toast with info pertaining to the image.
        imageOfTheDay.setOnClickListener( (click) -> {
                Toast.makeText(ImageOfTheDay.this, toastMessage, Toast.LENGTH_LONG).show();
        });

        // This button opens the image's HD URL on the device's default browser.
        Button hdButton = findViewById(R.id.hdButtonIotD);
        hdButton.setOnClickListener( (click) -> {
             // Make this open a browser using the imageHDURL.
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
                welcomeTextIotD.setText("A blast from the past.");

            }, year, month, day);
            datePicker.show();
        });

        // This button saves the current image, adding it to the list view.
        Button saveButton = findViewById(R.id.saveButtonIotD);
        saveButton.setOnClickListener( (click) -> {
            // Add image to ListView
        });

        ProgressBar downloadProgress = findViewById(R.id.progressBar);
        downloadProgress.setVisibility(View.VISIBLE);
    }

//    public boolean fileExists(String fname){
//        File file = getBaseContext().getFileStreamPath(fname);
//        return file.exists();
//    }

    private class NASAImageQuery extends AsyncTask<String, Integer, String> {

        String date, explanation, imageHDURL, title, imageURL;
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

                // Pull API data.
                date = nasaImageData.getString("date");
                explanation = nasaImageData.getString("explanation");
                imageHDURL = nasaImageData.getString("hdurl");
                title = nasaImageData.getString("title");
                imageURL = nasaImageData.getString("url");

                // get image
//                if (fileExists(imageURL)) {
//                    FileInputStream fis = null;
//                    try {
//                        fis = openFileInput(imageURL);
//                    }
//                    catch (FileNotFoundException e) {    e.printStackTrace();  }
//                    nasaImage = BitmapFactory.decodeStream(fis);
//
//                } else {
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
//                }
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

            toastMessage = explanation;
            hdButtonLink = imageHDURL;
            downloadProgress.setVisibility(View.INVISIBLE);
        }
    }
}