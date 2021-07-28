package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class AnimationTest extends AppCompatActivity {

    AnimationDrawable progressAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);

        ImageView background = (ImageView)findViewById(R.id.background);
        progressAnimation = (AnimationDrawable)background.getDrawable();
        progressAnimation.setCallback(background);
        progressAnimation.setVisible(true, true);
        background.post(new Starter());
    }

    class Starter implements Runnable {
        public void run() {
            progressAnimation.start();
        }
    }
}