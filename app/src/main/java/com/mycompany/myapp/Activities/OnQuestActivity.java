package com.mycompany.myapp.Activities;

import android.Manifest;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.mycompany.myapp.Objects.Quiz;
import com.mycompany.myapp.R;
import com.mycompany.myapp.Objects.User;

/**
 * This class represents the activity (Android window) for actively being on a quest.
 * It displays a map marking one's current location and the location of the next landmark
 * within the current quest, as well as a list with that landmark, a list of all landmarks
 * remaining after that one, and a progress bar showing quest completion with a percentage.
 *
 * Created by Ruben on 17-03-2016.
 */

//TODO: Quiz is not implemented yet
public class OnQuestActivity extends FragmentActivity implements OnMapReadyCallback {

    private ProgressBar mProgress; //the progress bar showing what percentage of the current quest has been completed thus far

    private Quest passedQuest; //the currently active quest, passed by the previous activity
    private Landmark nextLandmark; //the next landmark within the currently active quest, Cant be put local like Android studio says.
    private ListView listView; //the list lay-out to be filled with the name of the next landmark
    private ListView listView2; //the list lay-out to be filled with the names of the landmarks remaining after the next one
    private GoogleMap mMap; //the (Google) map
    private Marker landmarker; //the marker of the next landmark's location
    private Marker mylocmarker; //the marker of one's current location

    private LocationListener locationListener; //the instance for following one's moving location
    private LocationManager locationManager; //the instance for managing the location listener
    private Landmark currentTarget; //the next landmark within the currently active quest
    private int end; //an auxiliary variable to check whether the current quest will be completed after reaching the next landmark (1 == yes, 0 == no)

    private Quiz quiz; //the quiz corresponding to the current landmark
    private Button quizButton; //the button for starting a quiz about the current landmark
    private String[] items; //the available answers for the multiple choice questions

    /* Initialises the activity as described above after loading the passed quest from the database.
     * Next, checks if GPS is enabled, and starts a location listener instance if possible. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onquest);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(!passedQuest.getLandmarks().isEmpty()) {
            currentTarget = passedQuest.getLandmarks().get(0);
        }
        Log.d("TEST", passedQuest.toString());


        //progress of quest
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        if (passedQuest.getProgress() < Constants.TOTAL_QUEST_PROGRESS) {
            mProgress.setProgress(passedQuest.getProgress());
        }


        listView = (ListView) findViewById(R.id.listView3);
        listView2 = (ListView) findViewById(R.id.listView4);

        updateListViews(listView, listView2);



        //initialise the LocationManager and LocationListener
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
                //called when GPS is turned off
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        //TODO: change below permission question, worst case ask permission for AP 23 else do shitty location permsisson request
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
            //TODO: below could be annoying, for api > 22 it's no problem though?
            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constants.MINIMAL_GPS_TIME, Constants.MINIMAL_GPS_DISTANCE, locationListener);

            if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MINIMAL_GPS_TIME, Constants.MINIMAL_GPS_DISTANCE, locationListener);
        }


    /* Gets the landmarks remaining within the current quest from the database and updates
     * the two list views with the first of those landmarks and with all others, respectively. */
    void updateListViews(ListView listView, ListView listView2) {
        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        User user = helper.getUser(helper.getReadableDatabase());
        ArrayAdapter<Landmark> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getFirstLandmark(user.getActiveQuest()));
        listView.setAdapter(adapter);

        user.getActiveQuest().getLandmarks().remove(0);
        ArrayAdapter<Landmark> adapter2 =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getActiveQuest().getLandmarks());
        listView2.setAdapter(adapter2);
        user.getActiveQuest().getLandmarks().add(0, currentTarget);
    }


    /* Resumes the activity after having been paused. */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /* Returns the first landmark of the landmarks still remaining within the given quest. */
    private Landmark[] getFirstLandmark(Quest q) { //set current Landmark and return an array with that 1 element
        Landmark[] nextLandmarks = new Landmark[1];
        this.nextLandmark = q.getLandmarks().get(0);
        nextLandmarks[0] = this.nextLandmark;
        return nextLandmarks;
    }


    /*
     * Below is map stuff + asking permission, map ready etc.
     */

    /* Saves the map for further use. This is called automatically after the map has been prepared and is ready for use. */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //show the next landmark on the map
        if (passedQuest.getLandmarks() != null) {
            Landmark lm = passedQuest.getLandmarks().get(0);
            landmarker = mMap.addMarker(new MarkerOptions().position(lm.getLocation()).title(lm.getName()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lm.getLocation(), 12.0f));
        }
    }

    /* Handles one's updated location by moving their location marker on the map,
     * but not before checking whether the next landmark has been reached already.
     * Binds reaching the landmark to starting a new LandMarkPopUpActivity, while
     * awarding its worth in points to the current user. If this turns out to be the
     * last landmark within the current quest, a new QuestFinishedActivity is started
     * afterwards as well. Finally, if necessary, the lists and the progress bar are
     * updated, and the map camera is moved to keep the user and its target in view. */
    private void handleNewLocation(Location location) {
        Log.d(Constants.TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        final DatabaseHelper helper = new DatabaseHelper(getBaseContext()); //TODO: close database???
        final User user = helper.getUser(helper.getReadableDatabase());

        if (location.distanceTo(currentTarget.getLocationObject()) < 20) {
            //stop location updates
//            try {
//                locationManager.removeUpdates(locationListener);
//                Log.d("loc stop", "test inside try");
//            } catch (SecurityException e) {
//                Log.e("Security Exception", "No permission to get location: " + e);
//            }
            Log.d("loc stop", "test");
            // This gets called twice!!! Should only once
            int points = currentTarget.getPoints();
            user.addPoints(points);
            helper.updateInDatabase(helper, user);
            Toast.makeText(getApplicationContext(),
                    R.string.reachedLandmarkToast, Toast.LENGTH_LONG).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(OnQuestActivity.this);

            builder.setNegativeButton(getResources().getString(R.string.continueQuest_in_popup), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (end == 1) { //end of quest
                        //stop location updates
                        try {
                            locationManager.removeUpdates(locationListener);
                        } catch (SecurityException e) {
                            Log.e("Security Exception", "No permission to get location: " + e);
                        }
                        user.finishQuest(user.getActiveQuest());
                        helper.updateInDatabase(helper, user);

                        Intent in = new Intent(getBaseContext(), QuestFinishedActivity.class);
                        in.putExtra("finishedQuest", passedQuest);
                        startActivity(in);
                        finish();
                    } else {
                        dialog.cancel();
                    }
                }
            });

            //TODO add the quiz functionality back for the beta version
//
//            builder.setPositiveButton("QUIZ", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    quiz = currentTarget.getQuiz();
//                    //items = quiz.getPossibleAnswers();
//                    items = new String[]{"answer A", "answer B", "answer C"};
//                    AlertDialog.Builder builder = new AlertDialog.Builder(OnQuestActivity.this);
//                    //builder.setTitle(quiz.getQuestion());
//                    builder.setTitle("Examplequestion?");
//                    builder.setItems(items, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int item) {
//                            //do something with the selection
//                            Toast.makeText(getApplicationContext(),
//                                    items[item], Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//
//                }
//            });

            AlertDialog alert = builder.create();
            alert.setTitle(getResources().getString(R.string.landmarkFound_PopupWindow));
            alert.setMessage(currentTarget.getInformation());
            alert.show();

            user.getActiveQuest().getLandmarks().remove(0); //TODO this should be changed to iscompleted
            if (user.getActiveQuest().getLandmarks().isEmpty()) {
                end = 1;
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
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon2));
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

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getBaseContext(), ContinueQuestActivity.class);
        startActivity(i);
        return;
    }
}
