package com.hoostery.user.hoostery.Search.ActivitySearchRightMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.hoostery.user.hoostery.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by valerio on 30/05/17.
 */

public class Tab1_FilterDate extends Fragment {

    View mainLayout;
    LinearLayout cuisineListLayout;
    JSONObject resultJSONObject;
    JSONArray timesJSONArray;
    JSONArray numPeopleJSONArray;
    String[] timesStringArray;
    String[] numPeopleStringArray;
    NumberPicker chooseTime;
    NumberPicker chooseNumPeople;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainLayout = (View) inflater.inflate(R.layout.activity_search_filter_tab1, container, false);

        try {
            resultJSONObject = new JSONObject(getArguments().getString("inputJSON"));
            timesJSONArray = resultJSONObject.getJSONArray("time");
            numPeopleJSONArray = resultJSONObject.getJSONArray("num_people");

//            cuisineListLayout = (LinearLayout) mainLayout.findViewById(R.id.cuisine_list_layout);
            chooseTime = (NumberPicker) mainLayout.findViewById(R.id.time_number_picker);
            chooseTime.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            chooseNumPeople = (NumberPicker) mainLayout.findViewById(R.id.num_people_number_picker);
            chooseNumPeople.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            int i,n;
            timesStringArray = new String[timesJSONArray.length()];
            for (i=0, n=timesJSONArray.length(); i<n; i++) {

                timesStringArray[i] = timesJSONArray.getString(i);
            }
            chooseTime.setMinValue(0);
            chooseTime.setMaxValue(i-1);
            chooseTime.setDisplayedValues(timesStringArray);

            numPeopleStringArray = new String[numPeopleJSONArray.length()];
            for (i=0, n=numPeopleJSONArray.length(); i<n; i++) {

                numPeopleStringArray[i] = numPeopleJSONArray.getString(i);
            }
            chooseNumPeople.setMinValue(0);
            chooseNumPeople.setMaxValue(i-1);
            chooseNumPeople.setDisplayedValues(numPeopleStringArray);

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }

        return mainLayout;
//        return inflater.inflate(R.layout.activity_search_filter_tab1, container, false);
    }
}

