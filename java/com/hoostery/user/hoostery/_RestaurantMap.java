package com.hoostery.user.hoostery;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by valerio on 19/04/17.
 */

public class _RestaurantMap extends FragmentActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Helloooooooo!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_map);

        mapView = (MapView) findViewById(R.id.map);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        System.out.println("Helloooooooo!");
    }


       /* Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        addresses = geocoder.getFromLocationName("Viale Bruno Pelizzi 21", 1);

        if(addresses.size() > 0) {
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();
        }*/


}
