package com.hoostery.user.hoostery.MainActivity.LeftMenu.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hoostery.user.hoostery.MainActivity.ArrayListAdapterBookingsHistory;
import com.hoostery.user.hoostery.MainActivity.MainActivity;
import com.hoostery.user.hoostery.RestaurantProfile.RestaurantProfile;

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

/**
 * Created by valerio on 04/06/17.
 */

public class ThreadGetLogin extends AsyncTask<Void, Void, String> {

    private Activity activity;
    Context parentContext = null;
    TextView errorMessageTextview;
    String previousActivityName;

    public ThreadGetLogin(Context context, String name, TextView text){

        parentContext = context;
        activity = (Activity) context;
        previousActivityName = name;
        errorMessageTextview = text;
    }

    @Override
    protected String doInBackground(Void... urls) {

        try {

            URL url = new URL("http://88.99.185.13/get_login.php");
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

        JSONObject obj = null;

        if (result.trim().equals("-1") == false) {

            try {

                obj = new JSONObject(result);

                SharedPreferences prefs = parentContext.getSharedPreferences("login", parentContext.MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("logged", "yes");
                editor.putString("first_name", (obj.getString("first_name")));
                editor.putString("last_name", (obj.getString("last_name")));
                editor.putString("email", (obj.getString("email")));
                editor.commit();
//
//                for (int i=0, n=obj.length(); i<n; i++) {
//
//                    JSONObject jsonobject = obj.getJSONObject(i);
//                    editor.putString("logged", "yes");
//                list.add(new String[]{jsonobject.getString("name"), jsonobject.getString("address"), jsonobject.getString("image")});
//                }


                if (previousActivityName.trim().equals("MainActivity")) {

                    Intent intent = new Intent(parentContext, MainActivity.class);
                    parentContext.startActivity(intent);

                } else if (previousActivityName.trim().equals("RestaurantProfile")) {

                    Intent intent = new Intent(parentContext, RestaurantProfile.class);
                    parentContext.startActivity(intent);
                }

            } catch (JSONException e) {

                System.out.println("JSONException: "+e.getMessage());
            }

            errorMessageTextview.setVisibility(View.GONE);

        } else {

            errorMessageTextview.setVisibility(View.VISIBLE);
        }


    }

}
