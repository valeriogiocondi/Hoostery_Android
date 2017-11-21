package com.hoostery.user.hoostery.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hoostery.user.hoostery.MainActivity.LeftMenu.Login.ActivityLogin;
import com.hoostery.user.hoostery.MainActivity.MainActivity;
import com.hoostery.user.hoostery.R;

public class ActivityAutocomplete extends AppCompatActivity {

    private PlaceAutocompleteFragment autocompleteFragment;
    Marker startMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_autocomplete);


        // Google Autocomplete
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();
        autocompleteFragment.setFilter(typeFilter);


        // Autocomplete-GoogleMaps

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Intent intent = new Intent(ActivityAutocomplete.this, ActivitySearch.class);
                intent.putExtra("lat", String.valueOf(place.getLatLng().latitude));
                intent.putExtra("lng", String.valueOf(place.getLatLng().longitude));
                ActivityAutocomplete.this.startActivity(intent);

//                System.out.println("Place: " + place.getName());//get place details here
//                System.out.println("Place: " + place.getAddress());//get place details here
/*

                this piece of code is into ActivitySearch.java

                if (startMarker != null)
                    startMarker.remove();

                startMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_start_pin_red))
                );
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude), 17), 2000,
                        new GoogleMap.CancelableCallback() {
                            @Override
                            public void onFinish() {
//                                makeRestaurantsRectangleSearch(googleMap);
                            }

                            @Override
                            public void onCancel() {
//                                Toast.makeText(getBaseContext(), "Animation to Sydney canceled", Toast.LENGTH_SHORT).show();
                            }
                        });
*/
            }

            @Override
            public void onError(Status status) {
                System.out.println("An error occurred: " + status);
            }
        });

    }
}
