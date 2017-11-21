package com.hoostery.user.hoostery.RestaurantProfile;

import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by valerio on 17/04/17.
 */

public class ThreadGetImageFromURL extends AsyncTask<String, Void, Bitmap> {

    ImageView imageField;

    public ThreadGetImageFromURL(ImageView image) {

        imageField = image;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (Exception e) {

            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        imageField.setImageBitmap(bitmap);
    }
}

/*
public class ThreadGetImageFromURL extends AsyncTask<String, Void, Bitmap> {

    View customView = null;
    ImageView image;

    public ThreadGetImageFromURL(View view, ImageView bmImage) {

        this.image = bmImage;
        customView = view;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (Exception e) {

            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        ImageView profileImageView = (ImageView) customView.findViewById(R.id.image_profile);
        profileImageView.setImageBitmap(bitmap);
    }
}*/
