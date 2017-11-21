package com.hoostery.user.hoostery.RestaurantProfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hoostery.user.hoostery.R;

public class RestaurantPhotoGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile_photo_gallery);

        // Go back to main activity
        ImageView iconButton = (ImageView) findViewById(R.id.back_to_main_activity);

        iconButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
            }
        });

        // Go back to main activity
        /*ImageView iconButton = (ImageView) findViewById(R.id.back_to_main_activity);

        iconButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
            }
        });*/

    }
}
