package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class TitlePage extends AppCompatActivity {

    AnimationDrawable bgAnimation;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_page);

        // Run animation for background
        background = findViewById(R.id.backgroundAnimation);
        bgAnimation = (AnimationDrawable) background.getDrawable();
        bgAnimation.setCallback(background);
        bgAnimation.setVisible(true, true);
        background.post(new Starter());

        Button enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener((click) -> {
            Intent enterApp = new Intent(TitlePage.this, MainActivity.class);
            startActivity(enterApp);
        });
    }

    class Starter implements Runnable {
        public void run() {
            bgAnimation.start();
        }
    }
}