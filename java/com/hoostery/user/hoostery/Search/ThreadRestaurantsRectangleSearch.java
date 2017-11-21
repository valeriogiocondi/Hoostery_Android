package com.hoostery.user.hoostery.Search;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hoostery.user.hoostery.R;
import com.hoostery.user.hoostery.RestaurantProfile.RestaurantProfile;
import com.hoostery.user.hoostery.Search.ActivitySearch;
import com.hoostery.user.hoostery.Search.ActivitySearchLeftMenu.ArrayAdapterRestaurantsResult;

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
 * Created by valerio on 23/05/17.
 */

public class ThreadRestaurantsRectangleSearch extends AsyncTask<Object, Void, String> {

    Context parentContext = null;
    private ActivitySearch parentView;
    GoogleMap map = null;
    private ListView restaurantsListView;
    Marker startMarker;
    Double[] getFromAutocompletePlace = new Double[2];



    public ThreadRestaurantsRectangleSearch(Context context, Double[] place){

        parentContext = context;
        parentView = (ActivitySearch) context;
        restaurantsListView = (ListView) parentView.findViewById(R.id.restaurants_search_list_view);
        getFromAutocompletePlace = place;
    }

    @Override
    protected String doInBackground(Object... params) {

//        Toast.makeText(parentContext, "topRight: "+squareCoords[0], Toast.LENGTH_SHORT).show();

        try {

            LatLng farLeft = (LatLng) params[0];
            LatLng farRight = (LatLng) params[1];
            LatLng nearLeft = (LatLng) params[2];
            LatLng nearRight = (LatLng) params[3];
            map = (GoogleMap) params[4];

            String urlStr = "";
            urlStr += "http://88.99.185.13/restaurant_rectangle_search.php?";
            urlStr += "topleft="+farLeft.latitude+" "+farLeft.longitude;
            urlStr += "&topright="+farRight.latitude+" "+farRight.longitude;
            urlStr += "&bottomleft="+nearLeft.latitude+" "+nearLeft.longitude;
            urlStr += "&bottomright="+nearRight.latitude+" "+nearRight.longitude;

            URL url = new URL(urlStr);
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
        JSONArray resultRestaurantJSONarray = null;
        ArrayList<String[]> list = new ArrayList<String[]>();


        try {

            resultRestaurantJSONarray = new JSONArray(result);

            map.clear();

            startMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(getFromAutocompletePlace[0], getFromAutocompletePlace[1]))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_start_pin_red))
            );

           for (int i=0, n=resultRestaurantJSONarray.length(); i<n; i++) {

                // RIEMPIRE I CAMPI LAT E LNG
                obj = resultRestaurantJSONarray.getJSONObject(i);
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(obj.getString("lat")), Double.parseDouble(obj.getString("lng"))))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red_32x32))
                );

               list.add(new String[]{obj.getString("name"), obj.getString("address"), obj.getString("profile_image")});
            }

            ListAdapter restaurantsAdapter = new ArrayAdapterRestaurantsResult(parentContext, list);
            restaurantsListView.setAdapter(restaurantsAdapter);

        } catch (JSONException e) {

            System.out.println("JSONException: "+e.getMessage());
        }

        restaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parentContext, RestaurantProfile.class);
                intent.putExtra("name", "Ciao");
                parentContext.startActivity(intent);
            }
        });

    }
}
