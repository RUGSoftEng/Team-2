package com.mycompany.myapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here.
 */
public class QuestPreviewActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final String TAG = QuestPreviewActivity.class.getSimpleName();

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap mMap; //might be null if Google Play services APK is not available

    private GoogleApiClient mGoogleApiClient; //field description goes here
    private LocationRequest mLocationRequest; //field description goes here
    private List<Marker> markers; //field description goes here

    private Quest passedQuest; //field description goes here
    private User currentUser; //field description goes here

    private DatabaseHelper dbhelper; //field description goes here
    private Button pickQuest; //field description goes here
    private Button startQuest; //field description goes here

    /* Method description goes here. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");
        // Get the database and get the user from it.
        dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        currentUser = dbhelper.getUser(db);

        setContentView(R.layout.activity_questpreview);
        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * Constants.MILLISEC)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * Constants.MILLISEC); // 1 second, in milliseconds

        ListView listView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<Landmark> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passedQuest.getLandmarks());
        listView.setAdapter(adapter);

        //redirect to quest content page, passing the chosen quest
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Landmark selectedlm = (Landmark) parent.getAdapter().getItem(position);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedlm.getLocation()));


            }
        });

        pickQuest = (Button) findViewById(R.id.addButton_questPreview);
        startQuest = (Button) findViewById(R.id.startButton_questPreview);

        pickQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.addQuest(passedQuest);
                dbhelper.updateInDatabase(dbhelper, currentUser);
                pickQuest.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                        "Quest added to your list!", Toast.LENGTH_LONG).show();
                startQuest.setVisibility(View.VISIBLE);
            }
        });

        startQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setActiveQuest(passedQuest);
                dbhelper.updateInDatabase(dbhelper, currentUser);
                Intent i = new Intent(getBaseContext(), OnQuestActivity.class);
                i.putExtra("PassedQuest", passedQuest);
                startActivity(i);
            }
        });

    }

    /* Method description goes here. */
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();

        //check if quest is already in Users quest and change according to (don't) show pickquest button
        currentUser = dbhelper.getUser(dbhelper.getReadableDatabase());
        for(Quest q : currentUser.getCurrentQuests()) {
            if (q.getID() == passedQuest.getID()) {
                Log.d("TEST", "passedQuest is in currentUser, onResume()");
                pickQuest.setVisibility(View.GONE); // remove the 'pick this quest' button
            }
        }
    }

    /* Method description goes here. */
    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /** Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
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
     * method in {@link #onResume()} to guarantee that it will be called. */
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

    /** This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null. */
    private void setUpMap() {
        // Get the locations of the landmarks in this quest
        Marker testmark;
        markers = new ArrayList<>();
        for (Landmark landmark : passedQuest.getLandmarks()) {
            testmark = mMap.addMarker(new MarkerOptions()
                    .position(landmark.getLocation())
                    .title(landmark.getName()));
            markers.add(testmark);
        }
    }

    /* Method description goes here. */
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon2)) ;
        mMap.addMarker(options);
        // Loop through the landmarklocations to make sure they are all displayed in the map
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        builder.include(options.getPosition());
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, Constants.PADDING);
        mMap.animateCamera(cu);
    }

    /* Method description goes here. */
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }

    /* Method description goes here. */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /* Method description goes here. */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /* Method description goes here. */
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
