package com.example.nasaiotd;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ImageList extends AppCompatActivity {

    private ArrayList<SavedImage> savedImages = new ArrayList<>();
    private SavedImagesAdapter savedImagesAdapter;
    SQLiteDatabase db;

    /** Fragment */
    DetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_image_list);

        /** Populate ListView */
        ListView picList = findViewById(R.id.picList);

        loadImagesFromDB();
        picList.setAdapter(savedImagesAdapter = new SavedImagesAdapter());
        /** Populate ListView end */

        /** Toolbar and Navigation Bar Code */
        Toolbar toolbar = findViewById(R.id.toolbar);

        /** Navigation Drawer code */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        /** Toolbar and Navigation Bar Code Ends */

        /** Go Back To Homepage button */
        Button goBack = findViewById(R.id.goBack);


        /** setOnClickListener for Go Back to Homepage button to point back to homepage */
        goBack.setOnClickListener( (click) -> {
            Intent homepage = new Intent(ImageList.this, MainActivity.class);
            startActivity(homepage);
        });

        /** Deleting A saved Image */
        picList.setOnItemLongClickListener((AdapterView<?> p, View v, int position, long id) -> {
            SavedImage svdImg = savedImages.get(position);
            String title = svdImg.getImageTitle();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this image?")
                .setMessage("Title:" + title)
                .setPositiveButton("Yes", (click, arg) -> {
                    picList.removeViewInLayout(v);
                    deleteImageFromDB(svdImg);
                    savedImages.remove(position);
                    savedImagesAdapter.notifyDataSetChanged();
                })
                .create().show();
            return true;
        });

        /** Creating a bundle for Fragment section */
        picList.setOnItemClickListener((list, view, position, id) -> {
            //Bundle
            Bundle dataToPass = new Bundle();
            SavedImage svImg = savedImages.get(position);
            dataToPass.putString("imageTitle", svImg.getImageTitle());
            dataToPass.putString("imageDate", svImg.getImageDate());
            dataToPass.putString("imageURL", svImg.getImageURL());
            Intent nextActivity = new Intent(ImageList.this, EmptyActivity.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity);

        });

    }

    /** Toolbar Functions */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflating the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /** This function here will start activities based on what item is selected from Toolbar */
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
                alertDialogBuilder.setTitle(R.string.svdTxtTitle)
                        .setMessage(R.string.svdTxt)
                        .setPositiveButton("Ok", (click, arg) -> {
                        })
                        .create().show(); //creating the dialog
                break;
        }
        return true;
    }

    /** This function will start activities based on what is selected from the navigation drawer */
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
            case R.id.help:
                /** Alert Dialogue goes here for help (instructions) */
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(R.string.svdTxtTitle)
                        .setMessage(R.string.svdTxt)
                        .setPositiveButton("Ok", (click, arg) -> {
                        })
                        .create().show(); //creating the dialog
                break;
        }
        return false;
    }
    /** Toolbar Functions end */

    /** Database handling */
    private void deleteImageFromDB(SavedImage savedImage) {
        db.delete(ImageDBOpener.TABLE_NAME, ImageDBOpener.COL_ID + "= ?", new String[] {Long.toString(savedImage.getImageID())});
    }

    private void loadImagesFromDB() {

        // Get a DB connection:
        ImageDBOpener imageDBOpener = new ImageDBOpener(this);
        db = imageDBOpener.getWritableDatabase();

        // Get all columns.
        String[] columns = {ImageDBOpener.COL_ID,
                ImageDBOpener.COL_TITLE,
                ImageDBOpener.COL_DATE,
                ImageDBOpener.COL_DESCRIPTION,
                ImageDBOpener.COL_URL,
                ImageDBOpener.COL_HDURL};

        // Query all results from the DB:
        Cursor results = db.query(false, ImageDBOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        // Find the column indices:
        int idColIndex = results.getColumnIndex(ImageDBOpener.COL_ID);
        int titleColIndex = results.getColumnIndex(ImageDBOpener.COL_TITLE);
        int dateColIndex = results.getColumnIndex(ImageDBOpener.COL_DATE);
        int descriptionColIndex = results.getColumnIndex(ImageDBOpener.COL_DESCRIPTION);
        int urlColIndex = results.getColumnIndex(ImageDBOpener.COL_URL);
        int hdurlColIndex = results.getColumnIndex(ImageDBOpener.COL_HDURL);

        // Iterate over the results and return true if there is a next item:
        while(results.moveToNext()) {
            long id = results.getLong(idColIndex);
            String title = results.getString(titleColIndex);
            String date = results.getString(dateColIndex);
            String description = results.getString(descriptionColIndex);
            String url = results.getString(urlColIndex);
            String hdurl = results.getString(hdurlColIndex);

            // Add the savedImage to the array list:
            savedImages.add(new SavedImage(id, title, date, description, url, hdurl));
        }
    }
    /** Database handling end */

    /** ListView Adapter */
    private class SavedImagesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return savedImages.size();
        }

        @Override
        public SavedImage getItem(int position) {
            return savedImages.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View savedImageView = convertView;
            LayoutInflater inflater = getLayoutInflater();

            savedImageView = inflater.inflate(R.layout.saved_image_view, parent, false);
            TextView savedImageTitle = savedImageView.findViewById(R.id.savedImageTitle);
            savedImageTitle.setText(savedImages.get(position).getImageTitle());
            TextView savedImageDate = savedImageView.findViewById(R.id.savedImageDate);
            savedImageDate.setText(savedImages.get(position).getImageDate());

            return savedImageView;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getImageID();
        }
    }
    /** ListView Adapter end */
}

