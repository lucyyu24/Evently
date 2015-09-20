package com.example.lucy.evently;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Firebase fireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        fireBase = new Firebase("https://evently.firebaseio.com/events");

        DecimalFormat formatter = new DecimalFormat("00");
        Calendar c = Calendar.getInstance();
        String year = formatter.format(c.get(Calendar.YEAR));
        String month = formatter.format(c.get(Calendar.MONTH));
        String day = formatter.format(c.get(Calendar.DAY_OF_MONTH));
        final String curDate = year + "-" + month + "-" + day;
        Log.v("curdate is", curDate);

        Query query = fireBase.orderByChild("date").equalTo(curDate);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                AppEvent event = snapshot.getValue(AppEvent.class);
                LatLng currentLocation = new LatLng(event.getLatitude(), event.getLongitude());
                MarkerOptions markerOps = new MarkerOptions()
                        .position(currentLocation)
                        .title(event.getStartTime() + " -- "+ event.getEndTime())
                        .snippet(event.getDescription());
                Marker marker = mMap.addMarker(markerOps);
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String s) {
            }

            @Override
            public void onCancelled(FirebaseError fbe) {

            }
        });



        /*new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                Log.v("Cindy number of kids ", ""+snapshot.getChildrenCount());
                for (int i = 0; i < snapshot.getChildrenCount(); ++i) {
                    DataSnapshot cur = snapshot.getChildren().iterator().next();
                    Log.v("current key is ", cur.getKey());
                    Log.v("current val is ", cur.getValue().toString());
                    *//*for (int j = 0; j < cur.getChildrenCount(); ++j) {
                        DataSnapshot val = cur.getChildren().iterator().next();
                        Log.v("current key is ",val.getKey());
                        Log.v("current val is ", val.getValue().toString());
                    }*//*
                }
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String s) {
            }

            @Override
            public void onCancelled(FirebaseError fbe) {

            }
        }*/
        setContentView(R.layout.activity_maps);


        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        LatLng currentLatLng = new LatLng(43.471162, -80.547942);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

        // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
            {
                marker.showInfoWindow();
                return true;
            }
        });
    }

    @Override
    public void onLocationChanged(Location loc) {
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public void newEvent (View view) {
        Intent intent = new Intent(this, NewEvent.class);
        startActivity(intent);
    }

}
