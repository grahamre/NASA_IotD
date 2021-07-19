package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ImageList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        //Go Back To Homepage button
        Button goBack = findViewById(R.id.goBack);


        //setOnClickListener for Go Back to Homepage button to point back to homepage
        goBack.setOnClickListener( (click) -> {
            Intent homepage = new Intent(ImageList.this, MainActivity.class);
            startActivity(homepage);
        });

    }
}