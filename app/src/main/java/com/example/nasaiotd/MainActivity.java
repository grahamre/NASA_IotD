// Milestone 1 -
// July 19th 11:59PM (Optional): Both team member submits a pdf. The Github repository
// needs to have a branch called milestone_1 containing the code for milestone_1.

// Milestone number and date  	Requirements implemented 	  Bonus Marks available
// 1) July 19th                 1,2, 3, 11, 13                5

// 2.  The project must have at least 1 progress bar and at least 1 button.

// 3.  The project must have at least 1 edit text with appropriate text input method and at least 1
//     Toast and 1 Snackbar.

// 11. All activities must be integrated into a single working application, on a single emulator, and
//     must be uploaded to GitHub.

// 13. The functions and variables you write must be properly documented using JavaDoc comments.


package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox confirmBox = findViewById(R.id.confirmBox);
        Button enterBtn = findViewById(R.id.enterBtn);
        Button picBtn = findViewById(R.id.picBtn);

        /** Image of the day activity is activated */
        enterBtn.setOnClickListener( (click) -> {
            Intent imageOfaTheDay = new Intent(MainActivity.this, ImageOfTheDay.class);
            startActivity(imageOfaTheDay);
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