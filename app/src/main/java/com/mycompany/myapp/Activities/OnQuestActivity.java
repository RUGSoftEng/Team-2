package com.mycompany.myapp.Activities;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.myapp.Constants;
import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.R;
import com.mycompany.myapp.Objects.User;

/**
 * Created by Ruben on 17/03/2016.
 */
public class OnQuestActivity extends FragmentActivity implements OnMapReadyCallback {

    private ProgressBar mProgress;

    private Quest passedQuest;
    private Landmark nextLandmark;
    private ListView listView, listView2;
    private GoogleMap mMap;
    private Marker landmarker;
    private Marker mylocmarker;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private Landmark currentTarget;
    private int end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onquest);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");
        setUpMapIfNeeded();

        // TODO list should not be empty
        currentTarget = passedQuest.getLandmarks().get(0);

        Log.d("TEST", passedQuest.toString());


        //Progress of quest
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        if (passedQuest.getProgress() < Constants.TOTAL_QUEST_PROGRESS) {
            mProgress.setProgress(passedQuest.getProgress());
        }


        listView = (ListView) findViewById(R.id.listView3);
        listView2 = (ListView) findViewById(R.id.listView4);

        updateListViews(listView, listView2);



        // Initialize the locationManager and locationListener
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                handleNewLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                // Called when GPS is turned off
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);


    }

    void updateListViews(ListView listView, ListView listView2) {
        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        User user = helper.getUser(helper.getReadableDatabase());
        ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, getFirstLandmark(user.getActiveQuest()));
        listView.setAdapter(adapter);

        user.getActiveQuest().getLandmarks().remove(0);
        ArrayAdapter<Landmark> adapter2 =
                new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, user.getActiveQuest().getLandmarks());
        listView2.setAdapter(adapter2);
        user.getActiveQuest().getLandmarks().add(0, currentTarget);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    private Landmark[] getFirstLandmark(Quest q) { //set current Landmark and return an array with that 1 element
        Landmark[] nextLandmarks = new Landmark[1];
        this.nextLandmark = q.getLandmarks().get(0);
        nextLandmarks[0] = this.nextLandmark;
        return nextLandmarks;
    }


    /*
    * Below is map stuff + asking permission, map ready etc.
    * */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }



    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.onquestmap))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // Show the next landmark on the map
        if (passedQuest.getLandmarks() != null) {
            Landmark lm = passedQuest.getLandmarks().get(0);
            landmarker = mMap.addMarker(new MarkerOptions().position(lm.getLocation()).title(lm.getName()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lm.getLocation(), 12.0f));
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(Constants.TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        if (location.distanceTo(currentTarget.getLocationObject()) < 20) {
            Toast.makeText(getApplicationContext(),
                    "Reached landmark", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getBaseContext(), LandMarkPopUpActivity.class);
            i.putExtra("passedLandmark", currentTarget);
            startActivity(i);

            DatabaseHelper helper = new DatabaseHelper(getBaseContext()); //TODO: close database???
            User user = helper.getUser(helper.getReadableDatabase());
            user.getActiveQuest().getLandmarks().remove(0); //TODO this should be changed to iscompleted
            if (user.getActiveQuest().getLandmarks().isEmpty()) {
                if (end == 1) {// end of quest
                    // stop locationupdates
                    locationManager.removeUpdates(locationListener);
                    user.finishQuest(user.getActiveQuest());
                    Intent in = new Intent(getBaseContext(), QuestFinishedActivity.class);
                    in.putExtra("finishedQuest", passedQuest);
                    startActivity(in);
                    finish();
                } else {
                    end = 1;
                }
            } else {
                user.getActiveQuest().getVisitedLandmarks().add(currentTarget);
                helper.updateInDatabase(helper, user);
                currentTarget = user.getActiveQuest().getLandmarks().get(0);
                landmarker.setPosition(currentTarget.getLocation());
                landmarker.setTitle(currentTarget.getName());
                updateListViews(listView, listView2);
                mProgress.setProgress(user.getActiveQuest().getProgress());
               }
        }

        if (mylocmarker ==  null) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title("I am here!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon2));
            mylocmarker = mMap.addMarker(options);
        } else {
            mylocmarker.setPosition(latLng);
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(landmarker.getPosition());
        builder.include(mylocmarker.getPosition());
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, Constants.PADDING);
        mMap.animateCamera(cu);
    }
}
