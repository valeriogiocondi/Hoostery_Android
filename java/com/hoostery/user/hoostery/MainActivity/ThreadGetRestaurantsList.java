package com.hoostery.user.hoostery.MainActivity;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.String;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.os.AsyncTask;
import java.util.ArrayList;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

import com.hoostery.user.hoostery.MainActivity.LeftMenu.FragmentHome;
import com.hoostery.user.hoostery.R;
import com.hoostery.user.hoostery.RestaurantProfile.RestaurantProfile;
import com.hoostery.user.hoostery.Search.ActivitySearchLeftMenu.ArrayAdapterRestaurantsResult;

/**
 * Created by valerio on 10/04/17.
 *
 *
 * Spostare la lista all'interno dell'activity corrispondente, e poi passarla a questo AsyncTask
 */

public class ThreadGetRestaurantsList extends AsyncTask<Void, Void, String> {

    private ListView restaurantsListView;
    private Activity activity;
    Context parentContext = null;

    public ThreadGetRestaurantsList(Context context, ListView list){

        parentContext = context;
        activity = (Activity) context;
        restaurantsListView = list;
    }

    @Override
    protected String doInBackground(Void... urls) {

        try {

            URL url = new URL("http://88.99.185.13/get_list.php");
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

    protected void onPostExecute(String result) {

        JSONArray obj = null;
        ArrayList<String[]> list = new ArrayList<String[]>();

        try {

            obj = new JSONArray(result);

            for (int i=0, n=obj.length(); i<n; i++) {

                JSONObject jsonobject = obj.getJSONObject(i);
                list.add(new String[]{jsonobject.getString("name"), jsonobject.getString("address"), jsonobject.getString("image")});
            }

            ArrayAdapterRestaurantsResult restaurantsAdapter = new ArrayAdapterRestaurantsResult(parentContext, list);
            restaurantsListView.setAdapter(restaurantsAdapter);


        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }

        restaurantsListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parentContext, RestaurantProfile.class);
                intent.putExtra("name", "Ciao");
                parentContext.startActivity(intent);
            }
        });

    }

}
