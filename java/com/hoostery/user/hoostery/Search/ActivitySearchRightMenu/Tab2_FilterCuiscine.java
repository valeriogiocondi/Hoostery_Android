package com.hoostery.user.hoostery.Search.ActivitySearchRightMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.hoostery.user.hoostery.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by valerio on 30/05/17.
 */

public class Tab2_FilterCuiscine extends Fragment {

    View mainLayout;
    LinearLayout cuisineListLayout;
    JSONArray cuisineJSONArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainLayout = (View) inflater.inflate(R.layout.activity_search_filter_tab2, container, false);

        try {

            cuisineJSONArray = new JSONArray(getArguments().getString("inputJSON"));
            cuisineListLayout = (LinearLayout) mainLayout.findViewById(R.id.cuisine_list_layout);

            for (int i=0, n=cuisineJSONArray.length(); i<n; i++) {

                CheckBox checkbox = new CheckBox(mainLayout.getContext());
                checkbox.setText(cuisineJSONArray.getString(i));
                checkbox.setTextSize(20);
                checkbox.setTextColor(Color.WHITE);
                checkbox.setLinkTextColor(Color.RED);
                cuisineListLayout.addView(checkbox);
            }

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }

        return mainLayout;
//        return inflater.inflate(R.layout.activity_search_filter_tab2, container, false);
    }
}

