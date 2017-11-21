package com.hoostery.user.hoostery.RestaurantProfile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hoostery.user.hoostery.MainActivity.LeftMenu.ActivityFavorites;
import com.hoostery.user.hoostery.MainActivity.MainActivity;
import com.hoostery.user.hoostery.R;

public class RestaurantProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);

//        Bundle restaurantData = getIntent().getExtras();
//        if (restaurantData == null)
//            return;
//
//        String message = restaurantData.getString("name");


        // Go back to main activity
        ImageView iconButton = (ImageView) findViewById(R.id.back_to_main_activity);

        iconButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
            }
        });

//        PASSARE UN RIFERIMENTO A ThreadGetRestaurant, per fare la chiamata in modo giusto

        // AsyncTask
        ThreadGetRestaurant restaurant = new ThreadGetRestaurant(this);
        restaurant.execute();

        // PhotoGallery

        ImageView imageProfile = (ImageView) findViewById(R.id.image_profile);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(RestaurantProfile.this, RestaurantPhotoGallery.class);
                RestaurantProfile.this.startActivity(intent);
            }
        });
    }
}
