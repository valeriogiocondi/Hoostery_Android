package com.hoostery.user.hoostery.Search.ActivitySearchRightMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hoostery.user.hoostery.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by valerio on 30/05/17.
 */

public class Tab3_FilterPrice extends Fragment {

    View mainLayout;
    LinearLayout priceListLayout;
    JSONArray priceJSONArray;
    RadioGroup priceRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainLayout = (View) inflater.inflate(R.layout.activity_search_filter_tab3, container, false);

        try {

            priceJSONArray = new JSONArray(getArguments().getString("inputJSON"));
//            priceListLayout = (LinearLayout) mainLayout.findViewById(R.id.cuisine_list_layout);
            priceRadioGroup = (RadioGroup) mainLayout.findViewById(R.id.price_radiogroup);


            for (int i=0, n=priceJSONArray.length(); i<n; i++) {

                /*CheckBox checkbox = new CheckBox(mainLayout.getContext());
                checkbox.setText(priceList.getString(i));
                checkbox.setTextSize(20);
                checkbox.setTextColor(Color.WHITE);
                checkbox.setLinkTextColor(Color.RED);
                priceListLayout.addView(checkbox);*/

                RadioButton checkbox = new RadioButton(mainLayout.getContext());
                checkbox.setText(priceJSONArray.getString(i));
                checkbox.setTextSize(20);
                checkbox.setTextColor(Color.WHITE);
                priceRadioGroup.addView(checkbox);

            }

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }

        return mainLayout;
//        return inflater.inflate(R.layout.activity_search_filter_tab3, container, false);
    }
}

