package com.example.nasaiotd;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class ImageList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

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

        //Go Back To Homepage button
        Button goBack = findViewById(R.id.goBack);


        //setOnClickListener for Go Back to Homepage button to point back to homepage
        goBack.setOnClickListener( (click) -> {
            Intent homepage = new Intent(ImageList.this, MainActivity.class);
            startActivity(homepage);
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
                alertDialogBuilder.setTitle("Instructions for Home Page")
                        //what is the message
                        .setMessage("This is the Home Page of the app. " +
                                "\n 1. You can enter your name in the space provided." +
                                "\n 2. To enter the app, click the Enter the App Button" +
                                "\n 3. To Access your saved images, click the Access your saved images button.") //gives the position of the row selected
                        .setPositiveButton("Ok", (click, arg) -> {
                        })
                        .create().show(); //creating the dialog
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
                alertDialogBuilder.setTitle("Instructions for Home Page")
                        //what is the message
                        .setMessage("This is the Home Page of the app. " +
                                "\n 1. You can enter your name in the space provided." +
                                "\n 2. To enter the app, click the Enter the App Button" +
                                "\n 3. To Access your saved images, click the Access your saved images button.") //gives the position of the row selected
                        .setPositiveButton("Ok", (click, arg) -> {
                        })
                        .create().show(); //creating the dialog
                break;
        }
        return false;
    }
    /** Toolbar Functions end */
}