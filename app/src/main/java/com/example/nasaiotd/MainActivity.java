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
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = findViewById(R.id.loadingBar);
        Button enterBtn = findViewById(R.id.enterBtn);
        Button picBtn = findViewById(R.id.picbtn);

        //setting the visibility of Progress Bar
        /** trying to figure out where we can actually use the progress bar */
        //progressBar.setVisibility(View.VISIBLE);

        enterBtn.setOnClickListener( (click) -> {
            Intent imageOfaTheDay = new Intent(MainActivity.this, ImageOfTheDay.class);
            startActivity(imageOfaTheDay);
        });

        //setOnClickListener on Access Saved Picture Button to point to the listView Activity
        picBtn.setOnClickListener( (click) -> {
            Intent imageList = new Intent(MainActivity.this, ImageList.class);
            startActivity(imageList);
        });
    }
}