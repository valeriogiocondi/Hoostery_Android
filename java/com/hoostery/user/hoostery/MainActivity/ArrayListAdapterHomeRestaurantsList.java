package com.hoostery.user.hoostery.MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoostery.user.hoostery.RestaurantProfile.ThreadGetImageFromURL;
import com.hoostery.user.hoostery.R;

import java.util.ArrayList;

/**
 * Created by valerio on 28/05/17.
 */

public class ArrayListAdapterHomeRestaurantsList extends ArrayAdapter<String[]> {

    Context parentContext = null;

    public ArrayListAdapterHomeRestaurantsList(Context context, ArrayList<String[]> List)  {

        super(context, R.layout.activity_main_left_menu_voice_home, List);
        parentContext = context;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layout = LayoutInflater.from(getContext());
        View customView = layout.inflate(R.layout.activity_main_left_menu_voice_home_restaurants_list_adapter, parent, false);

        TextView nameTextView = (TextView) customView.findViewById(R.id.restaurant_name);
        TextView addressTextView = (TextView) customView.findViewById(R.id.restaurant_address);

        new ThreadGetImageFromURL((ImageView) customView.findViewById(R.id.image_profile)).execute(getItem(position)[2]);

        nameTextView.setText(getItem(position)[0]);
        addressTextView.setText(getItem(position)[1]);

        return customView;
    }
}
