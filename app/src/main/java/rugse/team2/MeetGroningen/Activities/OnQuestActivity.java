package rugse.team2.MeetGroningen.Activities;

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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import rugse.team2.MeetGroningen.Constants;
import rugse.team2.MeetGroningen.DatabaseStuff.DatabaseHelper;
import rugse.team2.MeetGroningen.Objects.Landmark;
import rugse.team2.MeetGroningen.Objects.Quest;
import rugse.team2.MeetGroningen.Objects.User;
import rugse.team2.MeetGroningen.R;

/**
 * This class represents the activity (Android window) for actively being on a quest.
 * It displays a map marking one's current location and the location of the next landmark
 * within the current quest, as well as a list with that landmark, a list of all landmarks
 * remaining after that one, and a progress bar showing quest completion with a percentage.
 *
 * Created by Ruben on 17-03-2016.
 */

public class OnQuestActivity extends FragmentActivity implements OnMapReadyCallback {
    /** the progress bar showing what percentage of the current quest has been completed thus far */
    private ProgressBar mProgress;

    /** the currently active quest, passed by the previous activity */
    private Quest passedQuest;
    /** the next landmark within the currently active quest */
    private Landmark nextLandmark; //cannot be put local like Android Studio says
    /** the list lay-out to be filled with the name of the next landmark */
    private ListView listView;
    /** the list lay-out to be filled with the names of the landmarks remaining after the next one */
    private ListView listView2;
    /** the (Google) map */
    private GoogleMap mMap;
    /** the marker of the next landmark's location */
    private Marker landmarker;
    /** the marker of one's current location */
    private Marker mylocmarker;

    /** the instance for following one's moving location */
    private LocationListener locationListener;
    /** the instance for managing the location listener */
    private LocationManager locationManager;
    /** the next landmark within the currently active quest */
    private Landmark currentTarget;
    /** an auxiliary variable to check whether the current quest will be completed after reaching the next landmark (1 == yes, 0 == no) */
    private int end;

    /** the button for starting a quiz about the current landmark */
    private Button quizButton;
    /** the available answers for a multiple choice question */
    private String[] possibleAnswers;

    /** the question for a multiple choice question */
    private String question;
    /** the correct answer for a multiple choice question */
    private String answer;

    /** Initialises the activity as described above after loading the passed quest from the database.
      * Next, checks if GPS is enabled, and starts a location listener instance if possible. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_onquest);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(rugse.team2.MeetGroningen.R.id.map);
        mapFragment.getMapAsync(this);

        if(!passedQuest.getLandmarks().isEmpty()) {
            currentTarget = passedQuest.getLandmarks().get(0);
        }
        Log.d("TEST", passedQuest.toString());


        //progress of quest
        mProgress = (ProgressBar) findViewById(rugse.team2.MeetGroningen.R.id.progressBar);
        if (passedQuest.getProgress() < Constants.TOTAL_QUEST_PROGRESS) {
            mProgress.setProgress(passedQuest.getProgress());
        }


        listView = (ListView) findViewById(rugse.team2.MeetGroningen.R.id.listView3);
        listView2 = (ListView) findViewById(rugse.team2.MeetGroningen.R.id.listView4);

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


        //TODO: change below permission question, worst case ask permission for AP 23 else do shitty location permission request
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
            //TODO: below could be annoying, for api > 22 it's no problem though?
            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constants.MINIMAL_GPS_TIME, Constants.MINIMAL_GPS_DISTANCE, locationListener);

            if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MINIMAL_GPS_TIME, Constants.MINIMAL_GPS_DISTANCE, locationListener);
        }


    /** Gets the landmarks remaining within the current quest from the database and updates
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


    /** Resumes the activity after having been paused. */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /** Returns the first landmark of the landmarks still remaining within the given quest. */
    private Landmark[] getFirstLandmark(Quest q) { //set current Landmark and return an array with that 1 element
        Landmark[] nextLandmarks = new Landmark[1];
        this.nextLandmark = q.getLandmarks().get(0);
        nextLandmarks[0] = this.nextLandmark;
        return nextLandmarks;
    }


    /*
     * Below is map stuff + asking permission, map ready etc.
     */

    /** Saves the map for further use. This is called automatically after the map has been prepared and is ready for use. */
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

    /** Handles one's updated location by moving their location marker on the map,
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

        if (location.distanceTo(currentTarget.getLocationObject()) < Constants.MAXIMAL_ACTIVATION_DISTANCE) {
            //stop location update
            int points = currentTarget.getPoints();
            user.addPoints(points);
            helper.updateInDatabase(helper, user);
            Toast.makeText(getApplicationContext(),
                    rugse.team2.MeetGroningen.R.string.reachedLandmarkToast, Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(OnQuestActivity.this);

            builder.setNegativeButton(getResources().getString(rugse.team2.MeetGroningen.R.string.continueQuest_in_popup), new DialogInterface.OnClickListener() {
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

                        dialog.cancel();
                        Intent in = new Intent(getBaseContext(), QuestFinishedActivity.class);
                        in.putExtra("finishedQuest", passedQuest);
                        startActivity(in);
                        finish();
                    } else {
                        dialog.cancel();
                    }
                }
            });

            if (currentTarget.getQuestion() != null) {
                question = currentTarget.getQuestion();
                possibleAnswers = currentTarget.getPossibleAnswers();
                answer = currentTarget.getAnswer();
                builder.setPositiveButton("QUIZ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OnQuestActivity.this);
                        builder.setTitle(question);
                        builder.setItems(possibleAnswers, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (possibleAnswers[item] == answer) {
                                    user.addPoints(5);
                                    helper.updateInDatabase(helper, user);
                                    Toast.makeText(getApplicationContext(),
                                            getResources().getString(R.string.rightAnswer), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            getResources().getString(R.string.wrongAnswer) + answer, Toast.LENGTH_LONG).show();
                                }
                                if (end == 1) { //end of quest
                                    //stop location updates
                                    try {
                                        locationManager.removeUpdates(locationListener);
                                    } catch (SecurityException e) {
                                        Log.e("Security Exception", "No permission to get location: " + e);
                                    }
                                    user.finishQuest(user.getActiveQuest());
                                    helper.updateInDatabase(helper, user);

                                    dialog.cancel();
                                    Intent in = new Intent(getBaseContext(), QuestFinishedActivity.class);
                                    in.putExtra("finishedQuest", passedQuest);
                                    startActivity(in);
                                    finish();
                                }
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });
            }

            AlertDialog alert = builder.create();
            alert.setTitle(getResources().getString(rugse.team2.MeetGroningen.R.string.landmarkFound_PopupWindow));
            alert.setMessage(currentTarget.getInformation());
            alert.show();

            user.getActiveQuest().getLandmarks().remove(0);
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
                    .title(getResources().getString(R.string.imhere))
                    .icon(BitmapDescriptorFactory.fromResource(rugse.team2.MeetGroningen.R.mipmap.icon2));
            mylocmarker = mMap.addMarker(options);
        } else {
            mylocmarker.setPosition(latLng);
        }
        mMap.setMyLocationEnabled(true);

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
