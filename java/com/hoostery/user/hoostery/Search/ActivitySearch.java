package com.hoostery.user.hoostery.Search;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.hoostery.user.hoostery.MainActivity.MainActivity;
import com.hoostery.user.hoostery.R;
import com.hoostery.user.hoostery.Search.ActivitySearchRightMenu.ThreadGetSearchFilterOptions;

public class ActivitySearch extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ConstraintLayout mainContent;
    private ConstraintLayout restaurantsListLeftMenu;
    private ConstraintLayout filterRightMenu;

    GoogleApiClient googleApiClient;
    Button searchInThisAreaButton;
    Animation fadeIn;
    Marker startMarker;
    Bundle previousActivityData;
    Double[] getFromAutocompletePlace = new Double[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //        new GetMap(ActivitySearch.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.search_map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mainContent = (ConstraintLayout) findViewById(R.id.content);
        restaurantsListLeftMenu = (ConstraintLayout) findViewById(R.id.result_search_restaurants);
        filterRightMenu = (ConstraintLayout) findViewById(R.id.filter_right_menu);

        previousActivityData = getIntent().getExtras();
        if (previousActivityData != null) {

            String str = previousActivityData.getString("lat");

            getFromAutocompletePlace[0] = Double.parseDouble(previousActivityData.getString("lat"));
            getFromAutocompletePlace[1] = Double.parseDouble(previousActivityData.getString("lng"));
        }

        // AsyncTask
//        final ThreadGetMap getMap = new ThreadGetMap(ActivitySearch.this);
//        getMap.execute();

        // AsyncTask
        final ThreadGetSearchFilterOptions filterOptions = new ThreadGetSearchFilterOptions(ActivitySearch.this);
        filterOptions.execute();

        TextView buttonRestaurantsList = (TextView) findViewById(R.id.go_to_restaurants_list);
        buttonRestaurantsList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                restaurantsListLeftMenu.setX(-mainContent.getWidth());

                for (int i=0, n=mainContent.getWidth(); i<=n; i++) {

                    restaurantsListLeftMenu.animate().x(-n+i).setDuration(250).start();
                    mainContent.animate().x(i).setDuration(250).start();
                }
            }
        });
        TextView buttonEditFilter = (TextView) findViewById(R.id.edit_filter);
        buttonEditFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                for (int i=0, n=mainContent.getWidth(); i<=n; i++) {

                    mainContent.animate().x(-i).setDuration(250).start();
                    restaurantsListLeftMenu.animate().x(-i).setDuration(250).start();
                }
            }
        });

        TextView buttonGoBackToSearchMapRestaurantResult = (TextView) findViewById(R.id.go_back_to_search_map_restaurantresult);
        buttonGoBackToSearchMapRestaurantResult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                for (int i=0, n=mainContent.getWidth(); i<=n; i++) {

                    mainContent.animate().x(n-i).setDuration(250).start();
                    restaurantsListLeftMenu.animate().x(-i).setDuration(250).start();
                }
            }
        });

        TextView buttonGoBackToSearchMapFilterPage = (TextView) findViewById(R.id.go_back_to_search_map_filter_page);
        buttonGoBackToSearchMapFilterPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                for (int i=0, n=mainContent.getWidth(); i<=n; i++) {

                    mainContent.animate().x(n-i).setDuration(250).start();
                }
            }
        });

        ConstraintLayout showAutocompleteImageView = (ConstraintLayout) findViewById(R.id.autocomplete_layout);
        showAutocompleteImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ActivitySearch.this, ActivityAutocomplete.class);
                ActivitySearch.this.startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        showCurrentLocation(googleMap);

        searchInThisAreaButton = (Button) findViewById(R.id.search_this_area);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        searchInThisAreaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                makeRestaurantsRectangleSearch(googleMap);

            }
        });

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

//                Button searchInThisAreaButton = (Button) findViewById(R.id.search_this_area);
//                Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                searchInThisAreaButton.startAnimation(fadeIn);

                searchInThisAreaButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        makeRestaurantsRectangleSearch(googleMap);

                    }
                });

            }
        });

        if (getFromAutocompletePlace[0] != null && getFromAutocompletePlace[1] != null) {

            // Autocomplete-GoogleMaps

            if (startMarker != null)
                startMarker.remove();

            startMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(getFromAutocompletePlace[0], getFromAutocompletePlace[1]))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_start_pin_red))
            );
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getFromAutocompletePlace[0], getFromAutocompletePlace[1]), 17), 2000,

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
        }


    // My Location Button

        ImageView myLocationButton = (ImageView) findViewById(R.id.my_location);
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //GPS - user could change him position, which is why location has to be createb every time
                showCurrentLocation(googleMap);

            }
        });

    }

    protected void showCurrentLocation(GoogleMap map) {

        boolean mLocationPermissionGranted = false;
        final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        Location mLastKnownLocation;
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if (mLastKnownLocation == null) {

                mLastKnownLocation = new Location("");
                mLastKnownLocation.setLatitude(41.853562);
                mLastKnownLocation.setLongitude(12.570842);
            }


            if (startMarker != null)
                startMarker.remove();

            startMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_start_pin_red))
            );
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 17));
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 17), 2000, null);

        }
    }


    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        // only stop if it's connected, otherwise we crash
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }

        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {

        // New location has now been determined
//        String msg = "Updated Location: " +
//                Double.toString(location.getLatitude()) + "," +
//                Double.toString(location.getLongitude());
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        boolean mLocationPermissionGranted = false;
        final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }


        // Get last known recent location.
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        // Note that this can be NULL if last location isn't already known.
        if (mCurrentLocation != null) {
            // Print current location if not null
//            System.out.println("current location: " + mCurrentLocation.toString());
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }
        // Begin polling for new location updates.
        startLocationUpdates();
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {


        long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
        long FASTEST_INTERVAL = 2000; /* 2 sec */


        // Create the location request
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void makeRestaurantsRectangleSearch(GoogleMap map) {

        VisibleRegion region = map.getProjection().getVisibleRegion();

        Object[] asyncTaskParams = new Object[5];
        asyncTaskParams[0] = region.farLeft;
        asyncTaskParams[1] = region.farRight;
        asyncTaskParams[2] = region.nearLeft;
        asyncTaskParams[3] = region.nearRight;
        asyncTaskParams[4] = map;

        // AsyncTask
        ThreadRestaurantsRectangleSearch makeSearch = new ThreadRestaurantsRectangleSearch(ActivitySearch.this, getFromAutocompletePlace);
        makeSearch.execute(asyncTaskParams);

        return;
    }
}
