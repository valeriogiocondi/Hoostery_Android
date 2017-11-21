package com.hoostery.user.hoostery.RestaurantProfile;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.hoostery.user.hoostery.R;
import com.hoostery.user.hoostery.RestaurantProfile.ItemButtonRestaurantChoosingTimeCustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class _GetRestaurantBookingSummary extends AsyncTask<Void, Void, String> {

    Context parentContext = null;
    View parentView = null;
//    ListView menuListView = null;
    Bundle intentData = null;

    public _GetRestaurantBookingSummary(Context context, Bundle intentData) {

        parentContext = context;
        parentView = ((Activity)parentContext).getWindow().getDecorView();
//        menuListView = (ListView) parentView.findViewById(R.id.menu_list_view);
        this.intentData = intentData;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {

            URL url = new URL("http://88.99.185.13/get_restaurant_time_available.php");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            InputStreamReader streamConnection = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamConnection);

            StringBuffer json = new StringBuffer(1024);
            String tmp;

            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            return json.toString();

        } catch (MalformedURLException e) {

            System.out.println(e.getMessage());

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        try {

            JSONObject jsonObj = new JSONObject(s);
            JSONArray lunchJSON = new JSONArray(jsonObj.getString("lunch"));
            JSONArray dinnerJSON = new JSONArray(jsonObj.getString("dinner"));

            ArrayList<String> lunchArrayList = new ArrayList<String>();
            ArrayList<String> dinnerArrayList = new ArrayList<String>();

            if (lunchJSON != null) {
                for (int i=0;i<lunchJSON.length();i++){
                    lunchArrayList.add(lunchJSON.getString(i));
                }
            }

            if (dinnerJSON != null) {
                for (int i=0;i<dinnerJSON.length();i++){
                    dinnerArrayList.add(dinnerJSON.getString(i));
                }
            }

            GridView gridviewLunch = (GridView) parentView.findViewById(R.id.gridview_lunch);
            GridView gridviewDinner = (GridView) parentView.findViewById(R.id.gridview_dinner);

            final List<String> lunchList = new ArrayList<String>(lunchArrayList);
            final List<String> dinnerList = new ArrayList<String>(dinnerArrayList);

            ItemButtonRestaurantChoosingTimeCustomAdapter gridViewLunchArrayAdapter = new ItemButtonRestaurantChoosingTimeCustomAdapter(parentContext, lunchList, intentData);
            ItemButtonRestaurantChoosingTimeCustomAdapter gridViewDinnerArrayAdapter = new ItemButtonRestaurantChoosingTimeCustomAdapter(parentContext, dinnerList, intentData);

            gridviewLunch.setAdapter(gridViewLunchArrayAdapter);
            gridviewDinner.setAdapter(gridViewDinnerArrayAdapter);

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }
    }

}
