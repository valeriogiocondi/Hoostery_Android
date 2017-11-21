package com.hoostery.user.hoostery.RestaurantProfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoostery.user.hoostery.MainActivity.LeftMenu.Login.ActivityLogin;
import com.hoostery.user.hoostery.MainActivity.MainActivity;
import com.hoostery.user.hoostery.R;

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
 * Created by valerio on 18/04/17.
 */

public class ThreadGetRestaurant extends AsyncTask<Void, Void, String> {

    Context parentContext = null;
    View parentView = null;
    JSONObject menuItemJSONobj = null;

    public ThreadGetRestaurant(Context context){

        parentContext = context;
        parentView = ((Activity)parentContext).getWindow().getDecorView();

    }

    @Override
    protected String doInBackground(Void... urls) {

        try {

            URL url = new URL("http://88.99.185.13/get_restaurant.php");
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

        System.out.println(result);

        JSONObject obj = null;
        TextView name;
        TextView address;
        ConstraintLayout mainLayout;
        ImageView staticMapImageView = (ImageView) parentView.findViewById(R.id.static_map);

        try {

            obj = new JSONObject(result);

            new ThreadGetImageFromURL((ImageView) parentView.findViewById(R.id.image_profile)).execute(obj.getString("image"));
            mainLayout = (ConstraintLayout) parentView.findViewById(R.id.constraint_layout_1);
            name = (TextView) parentView.findViewById(R.id.restaurant_name);
            address = (TextView) parentView.findViewById(R.id.address);

            name.setText(obj.getString("name"));
            address.setText(obj.getString("address"));


            // CREATE MENU LIST

            JSONArray menuListJSONarray = null;
            LinearLayout layoutMenuList = (LinearLayout) parentView.findViewById(R.id.layout_list);
            ArrayList<JSONObject> list = new ArrayList<JSONObject>();

            try {

                menuListJSONarray = new JSONArray(obj.getString("menu"));

                for (int i=0, n=menuListJSONarray.length(); i<n; i++) {

                    // CREATE LAYOUT

                    LinearLayout layoutItemMenuTemp = new LinearLayout(parentContext);
                    layoutItemMenuTemp.setOrientation(LinearLayout.VERTICAL);
                    layoutItemMenuTemp.setPadding(0, 100, 0, 100);
                    layoutItemMenuTemp.setBackgroundColor(Color.parseColor("#EEEEEE"));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 200, 0, 0);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    layoutItemMenuTemp.setLayoutParams(params);

                    // GET DATA

                    JSONArray menuContentJSONarray = null, plateItemJSONobejct = null;
                    menuItemJSONobj = menuListJSONarray.getJSONObject(i);

                    try {

                        menuContentJSONarray =  new JSONArray(menuItemJSONobj.getString("content"));

                    } catch (JSONException e) {

                        System.out.println("Error: "+e.getMessage());
                    }

                    // TITLE

                    TextView titleTempTextView = new TextView(parentContext);
                    titleTempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                    titleTempTextView.setGravity(Gravity.CENTER);
                    titleTempTextView.setText(menuItemJSONobj.getString("name"));
                    titleTempTextView.setPadding(0, 0, 0, 150);
                    layoutItemMenuTemp.addView(titleTempTextView);


                    for (int j=0, m=menuContentJSONarray.length(); j<m; j++) {

                        plateItemJSONobejct = menuContentJSONarray.getJSONArray(j);

                        TextView labelTempTextView = new TextView(parentContext);
                        labelTempTextView.setText(plateItemJSONobejct.getString(0));
                        labelTempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        labelTempTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                        layoutItemMenuTemp.addView(labelTempTextView);

                        TextView valueTempTextView = new TextView(parentContext);
                        valueTempTextView.setText(plateItemJSONobejct.getString(1));
                        valueTempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        valueTempTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                        valueTempTextView.setTextColor(Color.BLACK);
                        valueTempTextView.setTypeface(Typeface.DEFAULT_BOLD);
                        valueTempTextView.setPadding(0, 2, 0, 25);

                        layoutItemMenuTemp.addView(valueTempTextView);
                    }

                    Button bookingButtonTemp = new Button(parentContext);
                    bookingButtonTemp.setId(Integer.parseInt(menuItemJSONobj.getString("id")));
                    bookingButtonTemp.setText("PRENOTA");
                    bookingButtonTemp.setBackgroundColor(Color.parseColor("#FF3F51B5"));
                    bookingButtonTemp.setTextColor(Color.parseColor("#FFFFFF"));
                    bookingButtonTemp.setWidth(400);

                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 150, 0, 0);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    bookingButtonTemp.setLayoutParams(params);

                    final String menuContentStr = new String(menuItemJSONobj.toString());

                    bookingButtonTemp.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

//                            SHARED PREFERENCE

                            SharedPreferences prefs = parentContext.getSharedPreferences("login", parentContext.MODE_PRIVATE);
                            String textData = prefs.getString("logged", "");

                            if (textData.trim().equals("") == false) {

                                Intent intent = new Intent(parentContext, RestaurantBookingCalendar.class);
                                intent.putExtra("bookingDataJSON", menuContentStr);
                                parentContext.startActivity(intent);

                            } else {

                                Intent intent = new Intent(parentContext, ActivityLogin.class);
                                intent.putExtra("from", "RestaurantProfile");
                                parentContext.startActivity(intent);
                            }
                        }
                    });

                    layoutItemMenuTemp.addView(bookingButtonTemp);

                    layoutMenuList.addView(layoutItemMenuTemp);
                }

            } catch (JSONException e) {

                System.out.println("JSONException: "+e.getMessage());
            }

            String staticMapURL = "http://maps.google.com/maps/api/staticmap?center="+obj.getString("lat")+","+obj.getString("lng")+"&zoom=19&size="+staticMapImageView.getWidth()+"x"+staticMapImageView.getHeight()+"&sensor=false";
            new ThreadGetImageFromURL(staticMapImageView).execute(staticMapURL);

            System.out.println(staticMapURL);

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }

    }

}


