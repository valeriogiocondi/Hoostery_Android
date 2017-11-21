package com.hoostery.user.hoostery.RestaurantProfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hoostery.user.hoostery.R;

import org.json.JSONObject;

public class RestaurantChoosingTime extends AppCompatActivity {

    JSONObject dataBookingJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_choosing_time);

        // GET INTENT DATA

        Bundle menuData = getIntent().getExtras();
        if (menuData == null)
            return;

        /*try {

            dataBookingJSON = new JSONObject(menuData.get("bookingDataJSON").toString());

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }*/

        ThreadGetRestaurantBookingTimeAvailable timeAvailable = new ThreadGetRestaurantBookingTimeAvailable(RestaurantChoosingTime.this, menuData);
        timeAvailable.execute();

    }
}
