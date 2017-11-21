package com.hoostery.user.hoostery.Search.ActivitySearchRightMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hoostery.user.hoostery.Search.ActivitySearchRightMenu.Tab1_FilterDate;
import com.hoostery.user.hoostery.Search.ActivitySearchRightMenu.Tab2_FilterCuiscine;
import com.hoostery.user.hoostery.Search.ActivitySearchRightMenu.Tab3_FilterPrice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by valerio on 30/05/17.
 */

public class FilterTabHost extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"DATA", "CUCINA", "BUDGET"};
    int mNumOfTabs;
    private String resultJSON;
    private JSONObject resultJSONObj;
    private JSONArray[] tab1JSONArray;
    private JSONArray tab2JSONArray;
    private JSONArray tab3JSONArray;

    public FilterTabHost(FragmentManager fm, int NumOfTabs, String str) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        resultJSON = str;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: {

                Bundle bundle = new Bundle();

                try {

                    tab1JSONArray = new JSONArray[2];
                    resultJSONObj = new JSONObject(resultJSON);
                    JSONObject tab1JSONObj = new JSONObject();

                    tab1JSONObj.put("time", new JSONArray(resultJSONObj.getString("time")));
                    tab1JSONObj.put("num_people", new JSONArray(resultJSONObj.getString("num_people")));

                    bundle.putString("inputJSON", tab1JSONObj.toString());

                } catch (JSONException e) {

                    System.out.println("JSONException: "+e.getMessage());
                }

                Tab1_FilterDate tab1 = new Tab1_FilterDate();
                tab1.setArguments(bundle);

                return tab1;
            }
            case 1: {

                Bundle bundle = new Bundle();

                try {

                    resultJSONObj = new JSONObject(resultJSON);
                    tab2JSONArray = new JSONArray(resultJSONObj.getString("cuisine"));
                    bundle.putString("inputJSON", tab2JSONArray.toString());

                } catch (JSONException e) {

                    System.out.println("JSONException: "+e.getMessage());
                }

                Tab2_FilterCuiscine tab2 = new Tab2_FilterCuiscine();
                tab2.setArguments(bundle);
                return tab2;
            }
            case 2: {

                Bundle bundle = new Bundle();

                try {

                    resultJSONObj = new JSONObject(resultJSON);
                    tab3JSONArray = new JSONArray(resultJSONObj.getString("price"));
                    bundle.putString("inputJSON", tab3JSONArray.toString());

                } catch (JSONException e) {

                    System.out.println("JSONException: "+e.getMessage());
                }

                Tab3_FilterPrice tab3 = new Tab3_FilterPrice();
                tab3.setArguments(bundle);
                return tab3;
            }
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}