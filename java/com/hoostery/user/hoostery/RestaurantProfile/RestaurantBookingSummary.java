package com.hoostery.user.hoostery.RestaurantProfile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoostery.user.hoostery.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantBookingSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_booking_summary);

        Bundle menuData = getIntent().getExtras();
        if (menuData == null)
            return;

//        TextView label = (TextView) findViewById(R.id.textView);
//        label.setText(menuData.getString("bookingDataJSON").toString());

        JSONObject dataBookingJSON = null;
        JSONArray menuContentJSONarray = null, plateItemJSONobejct = null;

        TextView dayLabel = (TextView) findViewById(R.id.check_day);
        TextView timeLabel = (TextView) findViewById(R.id.check_time);
        TextView numPeopleLabel = (TextView) findViewById(R.id.check_num_people);
        TextView menuTitle = (TextView) findViewById(R.id.menu_title);

        try {

            dataBookingJSON = new JSONObject(menuData.getString("bookingDataJSON").toString());
            menuContentJSONarray =  new JSONArray(dataBookingJSON.getString("content"));

            dayLabel.setText(dataBookingJSON.getString("date_format_1"));
            timeLabel.setText(dataBookingJSON.getString("time"));
            numPeopleLabel.setText("3");
            menuTitle.setText(dataBookingJSON.getString("name"));

            LinearLayout menuLayout = (LinearLayout) findViewById(R.id.menu_content);

            for (int j=0, m=menuContentJSONarray.length(); j<m; j++) {

                plateItemJSONobejct = menuContentJSONarray.getJSONArray(j);

                TextView labelTempTextView = new TextView(this);
                labelTempTextView.setText(plateItemJSONobejct.getString(0));
                labelTempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                labelTempTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                menuLayout.addView(labelTempTextView);

                TextView valueTempTextView = new TextView(this);
                valueTempTextView.setText(plateItemJSONobejct.getString(1));
                valueTempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                valueTempTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                valueTempTextView.setTextColor(Color.BLACK);
                valueTempTextView.setTypeface(Typeface.DEFAULT_BOLD);
                valueTempTextView.setPadding(0, 2, 0, 25);

                menuLayout.addView(valueTempTextView);
            }

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }



//        NON SERVE
//        _GetRestaurantBookingSummary bookingSummary = new _GetRestaurantBookingSummary(RestaurantBookingSummary.this, menuData);
//        bookingSummary.execute();


    }
}
