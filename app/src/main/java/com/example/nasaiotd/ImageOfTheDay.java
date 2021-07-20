package com.example.nasaiotd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class ImageOfTheDay extends AppCompatActivity {

    // This is the NASA API key:
    // VV3DcWE3AbEdOJQqg6ROHRbFU6p9dRrDlM4ngREj

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_the_day);

        ProgressBar downloadProgress = findViewById(R.id.progressBar);
        downloadProgress.setVisibility(View.VISIBLE);

        // Query for image
        NASAImageQuery req = new NASAImageQuery();
//        req.execute();
    }

    private class NASAImageQuery extends AsyncTask<String, Integer, String> {

        ProgressBar downloadProgress = findViewById(R.id.progressBar);

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            downloadProgress.setProgress(value[0]);
        }

        @Override
        public void onPostExecute(String fromDoInBackground) {
            downloadProgress.setVisibility(View.INVISIBLE);
        }
    }
}


