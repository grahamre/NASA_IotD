package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    AnimationDrawable logoAnimation;
    private EditText userName;
    public static final String PREFERENCES = "MyPreference";
    public static final String USERNAME = "usrName";

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        Typeface exo = ResourcesCompat.getFont(this, R.font.exo);

        TextView welcomeText = findViewById(R.id.welcomeTxt);
        welcomeText.setTypeface(exo);

        // Run animation for logo
        ImageView logo = findViewById(R.id.logoImage);
        logoAnimation = (AnimationDrawable) logo.getDrawable();
        logoAnimation.setCallback(logo);
        logoAnimation.setVisible(true, true);
        logo.post(new MainActivity.Starter());

        // Get name from user
        EditText yourName = findViewById(R.id.yourName);
        name = yourName.getText().toString();

        /** Toolbar and Navigation Bar Code */
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Navigation drawer
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

        /** This edit text will collect a name for saved preferences */
        userName = (EditText) findViewById(R.id.yourName);

        /** SharedPreferences to display previously entered name in the EditText */
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String usrName = sharedPreferences.getString(USERNAME, "");
        userName.setText(usrName);

        /** Image of the day activity is activated */
        enterBtn.setOnClickListener( (click) -> {
            name = yourName.getText().toString();
            Intent imageOfTheDay = new Intent(MainActivity.this, ImageOfTheDay.class);
            imageOfTheDay.putExtra("name", name);
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

    /** This function here will save the user name entered to SharedPreference */
    @Override
    protected void onPause() {
        super.onPause();

        //Saving User Name to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putString(USERNAME, userName.getText().toString());
        preferenceEditor.apply();
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

    class Starter implements Runnable {
        public void run() {
            logoAnimation.start();
        }
    }
}