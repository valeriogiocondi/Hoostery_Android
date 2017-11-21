package com.hoostery.user.hoostery.RestaurantProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hoostery.user.hoostery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ItemButtonRestaurantChoosingTimeCustomAdapter extends ArrayAdapter<String> {

    Context parentContext = null;
    JSONObject dataBookingJSON = null;

    ItemButtonRestaurantChoosingTimeCustomAdapter(Context context, List<String> List, Bundle intentData)  {

        super(context, R.layout.item_button_restaurant_choosing_time, List);
        parentContext = context;

        try {

            dataBookingJSON = new JSONObject(intentData.get("bookingDataJSON").toString());

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layout = LayoutInflater.from(getContext());
        View customView = layout.inflate(R.layout.item_button_restaurant_choosing_time, parent, false);

        Button timeButton = (Button) customView.findViewById(R.id.time_button);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        timeButton.setLayoutParams(params);
        timeButton.setText(getItem(position));

        timeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // PUT DATA FOR NEXT INTENT

                try {

                    Button buttonPressed = (Button) v;
                    dataBookingJSON.put("time", buttonPressed.getText().toString());

                } catch (JSONException e) {

                    System.out.println("JSONException :"+e.getMessage());
                }

                Intent intent = new Intent(parentContext, RestaurantBookingSummary.class);
                intent.putExtra("bookingDataJSON", dataBookingJSON.toString());
                parentContext.startActivity(intent);

            }
        });

        return customView;
    }

}
