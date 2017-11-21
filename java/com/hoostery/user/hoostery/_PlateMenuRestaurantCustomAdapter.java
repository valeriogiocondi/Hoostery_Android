package com.hoostery.user.hoostery;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by valerio on 20/04/17.
 */

public class _PlateMenuRestaurantCustomAdapter extends ArrayAdapter<JSONObject> {

    Context parentContext = null;

    _PlateMenuRestaurantCustomAdapter(Context context, ArrayList<JSONObject> List)  {

        super(context, R.layout._item_plate_menu_restaurant, List);
        parentContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater restaurantsListLayout = LayoutInflater.from(getContext());
        View customView = restaurantsListLayout.inflate(R.layout._item_plate_menu_restaurant, parent, false);

        System.out.println(position);

        String[][] menuContent = null;
        JSONArray menuContentJSONarray = null, plateItemJSONobejct = null;

        try {

            menuContentJSONarray = new JSONArray(getItem(position).getString("content"));

            TextView title = (TextView) customView.findViewById(R.id.title);
            title.setText(getItem(position).getString("name"));

            LinearLayout menuContentLayout = (LinearLayout) customView.findViewById(R.id.menu_content_layout);

            for (int i=0, n=menuContentJSONarray.length(); i<n; i++) {

                plateItemJSONobejct = menuContentJSONarray.getJSONArray(i);

                TextView tempTextView_1 = new TextView(parentContext);
                tempTextView_1.setText(plateItemJSONobejct.getString(0));
                tempTextView_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                tempTextView_1.setGravity(Gravity.CENTER);
                menuContentLayout.addView(tempTextView_1);

                TextView tempTextView_2 = new TextView(parentContext);
                tempTextView_2.setText(plateItemJSONobejct.getString(1));
                tempTextView_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tempTextView_2.setGravity(Gravity.CENTER);
                tempTextView_2.setPadding(0, 2, 0, 25);

//                LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                paramsTextView.setMargins(0, 5, 0, 20);
//                tempTextView_2.setLayoutParams(paramsTextView);

                menuContentLayout.addView(tempTextView_2);
            }

        } catch (JSONException e) {

            System.out.println("Error: "+e.getMessage());
        }

        return customView;

    }

}
