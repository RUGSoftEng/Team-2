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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
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
 * This class represents the activity (Android window) for previewing a quest.
 * It gets the landmarks corresponding to a certain quest from the database and
 * displays them as markers in a map, together with one's own current location,
 * and shows their names in an ordered list too to provide a complete overview.
 */
public class QuestPreviewActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback {

    public static final String TAG = QuestPreviewActivity.class.getSimpleName();

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult .
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap mMap; //the (Google) map, might be null if the Google Play services APK is not available

    private GoogleApiClient mGoogleApiClient; //the main entry point for Google Play services integration
    private LocationRequest mLocationRequest; //the instance used for requesting one's location via Google Play services
    private List<Marker> markers; //the list of markers of landmark locations

    private Quest passedQuest; //the quest to be previewed, passed by the previous activity
    private User currentUser; //the current application user

    private DatabaseHelper dbhelper; //the helper instance for exchanging information with the database
    private Button pickQuest; //the button for adding the previewed quest to the current user's list of started quests
    private Button startQuest; //the button for directly going on the quest that has just been picked

    /* Initialises the activity as described above, and binds clicking 'pick' to adding the previewed
     * quest to the current user in the database and 'start' to starting a new OnQuestActivity.
     * Also makes clicking a landmark from the list move the map camera to focus on it. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");
        //get the database and get the user from it
        dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        currentUser = dbhelper.getUser(db);

        setContentView(R.layout.activity_questpreview);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * Constants.MILLISEC)        //10 seconds, in milliseconds
                .setFastestInterval(Constants.MILLISEC); //1 second, in milliseconds

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

    /* Resumes the activity after having been paused, reconnecting to Google Play services, and then hiding the
     * 'pick' button if it had been clicked already and hence was removed in favour of the 'start' button. */
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

        //check if quest is already in User's quests and change according to (don't) show pick quest button
        currentUser = dbhelper.getUser(dbhelper.getReadableDatabase());
        for(Quest q : currentUser.getCurrentQuests()) {
            if (q.getID().equals(passedQuest.getID())) {
                Log.d("TEST", "passedQuest is in currentUser, onResume()");
                //pickQuest.setVisibility(View.GONE); //remove the 'pick this quest' button
            }
        }
    }

    /* Pauses the activity, disconnecting from Google Play services until the activity is reopened (resumed). */
    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
       //get the locations of the landmarks in this quest
        mMap = map;
        Marker testmark;
        markers = new ArrayList<>();
        for (Landmark landmark : passedQuest.getLandmarks()) {
            testmark = mMap.addMarker(new MarkerOptions()
                    .position(landmark.getLocation())
                    .title(landmark.getName()));
            markers.add(testmark);
        }
     }


    /* Handles one's updated location by moving their location marker on the map, and by moving
     * the map camera to keep the user and all landmarks within the previewed quest in view. */
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon2)) ;
        mMap.addMarker(options);
        mMap.setMyLocationEnabled(true);
        //loop through the landmark locations to make sure they are all displayed in the map
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        builder.include(options.getPosition());
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, Constants.PADDING);
        mMap.animateCamera(cu);
    }

    /* Checks if GPS is enabled, finds one's current location if possible, and calls handleNewLocation to handle it. */
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

    /* Empty method that makes sure that nothing is done when the connection to Google Play services is suspended. */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /* Tries resolving an encountered error in connecting to Google Play
     * services automatically, and logs an error message if this fails. */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects. If
         * the error has a resolution, try sending an Intent to start
         * a Google Play services activity that can resolve the error.
         */
        if (connectionResult.hasResolution()) {
            try {
                //start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services cancelled the original PendingIntent.
                 */
            } catch (IntentSender.SendIntentException e) {
                //log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display
             * a dialog to the user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /* Is called whenever the current user's location changes, calling handleNewLocation to handle the updated location. */
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
