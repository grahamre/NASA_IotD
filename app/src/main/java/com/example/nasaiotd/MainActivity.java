package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
}