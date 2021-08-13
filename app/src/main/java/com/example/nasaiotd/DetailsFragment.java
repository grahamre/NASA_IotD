package com.example.nasaiotd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DetailsFragment extends Fragment {
    private Bundle pictureBundle;
    private String url;
    private AppCompatActivity parentActivity;
    private ImageView imgOTD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_details, container, false);
        pictureBundle = getArguments();

        TextView picTitle = result.findViewById(R.id.titleHere);
        picTitle.setText(pictureBundle.getString("imageTitle"));

        TextView picDate = result.findViewById(R.id.dateHere);
        picDate.setText(pictureBundle.getString("imageDate"));

        /** ImageButton imgOTD = result.findViewById(R.id.imgOfDay);
        imgOTD.setText(pictureBundle.getString("imageURL")); */
        imgOTD = (ImageView) result.findViewById(R.id.imgOfDay);
        url = pictureBundle.getString("imageURL");

        LoadImage req = new LoadImage();
        req.execute();
        /** Every single time Application crashes when I try to load this piece of Code
         *  I think it has something to do with taking quite a bit of time to download the image
         *  Something I need to work on Thursday.
         try {
            ImageView i = (ImageView)result.findViewById(R.id.imgOfDay);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(pictureBundle.getString("imageURL")).getContent());
            i.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } ****/

        // This was just a code I was trying but didn't work either.
        /** try {
            url = pictureBundle.getString("imageURL");
            URL imgURL = new URL(url);
            ImageView imgOTD = (ImageView) result.findViewById(R.id.imgOfDay);
            Bitmap bitmap = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
            imgOTD.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } */

        Button hideButton = (Button)result.findViewById(R.id.hideBtn);
        hideButton.setOnClickListener( click -> {
            //creates a fragment transaction which uses remove() to delete the fragment.
            parentActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();
        });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }

    public class LoadImage extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imgOTD.setImageBitmap(result);
        }
    }
}