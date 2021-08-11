package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        Typeface exo = ResourcesCompat.getFont(this, R.font.exo);

        TextView bigTitle = findViewById(R.id.bigTitle);
        bigTitle.setTypeface(exo);
        TextView byline = findViewById(R.id.byline);
        byline.setTypeface(exo);

        Button enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener((click) -> {
            Intent enterApp = new Intent(TitlePage.this, MainActivity.class);
            startActivity(enterApp);
            bgAnimation.stop();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    class Starter implements Runnable {
        public void run() {
            bgAnimation.start();
        }
    }
}