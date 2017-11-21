package com.hoostery.user.hoostery.RestaurantProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import com.hoostery.user.hoostery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RestaurantBookingCalendar extends AppCompatActivity {

    TextView tect = null;
//    JSONObject dataBookingJSON = new JSONObject();
    JSONObject dataBookingJSON = null;
    Bundle menuData = null;
    String[] dateSelected = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_booking_calendar);

        // GET INTENT DATA

        menuData = getIntent().getExtras();
        if (menuData == null)
            return;

        try {

            dataBookingJSON = new JSONObject(menuData.get("bookingDataJSON").toString());

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }

        // DATE PICKER

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dateSelected[0] = String.format("%02d", day)+"/"+String.format("%02d", month)+"/"+year;
        dateSelected[1] = year+"-"+String.format("%02d", month)+"-"+String.format("%02d", day);

        DatePicker bookingDatePicker = (DatePicker) findViewById(R.id.booking_calendar);
        bookingDatePicker.init(year, month, day, new OnDateChangedListener(){

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){

                dateSelected[0] = String.format("%02d", dayOfMonth)+"/"+String.format("%02d", monthOfYear)+"/"+year;
                dateSelected[1] = year+"-"+String.format("%02d", monthOfYear)+"-"+String.format("%02d", dayOfMonth);
            }
        });

        // BUTTON

        Button goToChoosingTime = (Button) findViewById(R.id.go_to_choosing_time);

        goToChoosingTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // PUT DATA FOR NEXT INTENT

                try {

                    dataBookingJSON.put("date_format_1", dateSelected[0]);
                    dataBookingJSON.put("date_format_2", dateSelected[1]);

                } catch (JSONException e) {

                    System.out.println("JSONException :"+e.getMessage());
                }

                Intent intent = new Intent(RestaurantBookingCalendar.this, RestaurantChoosingTime.class);
                intent.putExtra("bookingDataJSON", dataBookingJSON.toString());
                RestaurantBookingCalendar.this.startActivity(intent);

            }
        });

    }
}
