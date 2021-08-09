package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

/*
Milestone 2 -   July 26th 11:59PM (Optional): Both team member submits a pdf. The Github repository
                needs to have a branch called milestone_2 containing the code for milestone_2.

    Milestone number and date  	Requirements implemented 	  Bonus Marks available
    2) July 26th                    4, 6, 7, 9, 11, 13            5

    4.  The software must have at least 4 or more activities. Your activity must be accessible by
    selecting a graphical icon from a Toolbar, and NavigationDrawer. The top navigation
    layout should have the Activity’s title, and a version number.

    6.  Each activity must have a help menu item that displays an AlertDialog with instructions for
    how to use the interface.

    7.  There must be at least 1 other language supported by your Activity. Please use Canadian French
    as the secondary language if you do not you know a language other than English.

    9.  When retrieving data from an http server, the activity must use an AsyncTask.

    11. All activities must be integrated into a single working application, on a single emulator, and
    must be uploaded to GitHub.

    13. The functions and variables you write must be properly documented using JavaDoc comments.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        CheckBox confirmBox = findViewById(R.id.confirmBox);
        Button enterBtn = findViewById(R.id.enterBtn);
        Button picBtn = findViewById(R.id.picBtn);

        // This edit text will collect a name for saved preferences
        EditText eee = findViewById(R.id.yourName);

        /** Image of the day activity is activated */
        enterBtn.setOnClickListener( (click) -> {
            Intent imageOfTheDay = new Intent(MainActivity.this, ImageOfTheDay.class);
            startActivity(imageOfTheDay);
        });

        /** clicking this button will activate the activity that contains list view of images */
        picBtn.setOnClickListener( (click) -> {
            Intent imageList = new Intent(MainActivity.this, ImageList.class);
            startActivity(imageList);
        });

        /** Confirmation box to see if the user is human or a robot */
        confirmBox.setOnCheckedChangeListener( (box, b) -> {
            if(b) {
                Snackbar.make(confirmBox, confirmBox.getResources().getString(R.string.cbMessage1), Snackbar.LENGTH_LONG)
                        .setAction("Undo",click-> box.setChecked(!b))
                        .show();
            } else {
                Snackbar.make(confirmBox,confirmBox.getResources().getString(R.string.cbMessage2), Snackbar.LENGTH_LONG)
                        .setAction("Undo",click-> box.setChecked(!b))
                        .show();
            }
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
                break;
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