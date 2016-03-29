package com.mycompany.myapp;

import android.app.PendingIntent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by Ruben on 17/03/2016.
 */
//some callbacks and other notifications for losing connection to internet
public class OnQuestActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status> {

    private ProgressBar mProgress;

    private Quest passedQuest;
    private ArrayList<Geofence> mGeofenceList;
    private Landmark nextLandmark;
    private GoogleApiClient mGoogleApiClient;
    private User user;
    private PendingIntent mGeofencePendingIntent;
    private ListView listView, listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onquest);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");

        Log.d("TEST", passedQuest.toString());

        Button b = (Button) findViewById(R.id.button); //TODO instead of button this should be activated by geofences,(landmarkPopup should be merged with questActivity)
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LandMarkPopUp.class));
            }
        });

        mGeofenceList = new ArrayList<Geofence>();
        mGeofencePendingIntent = null;

        //Progress of quest
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        if (passedQuest.getProgress() < Constants.TOTAL_QUEST_PROGRESS) {
            mProgress.setProgress(passedQuest.getProgress());
        }


        listView = (ListView) findViewById(R.id.listView3);
        listView2 = (ListView) findViewById(R.id.listView4);

        /* TODO do we need below?? Already in onResume()
        //Next landmark in the list is the first from the passedQuest from the user
        ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, getFirstLandmark(passedQuest));
        listView.setAdapter(adapter);

        ArrayAdapter<Landmark> adapter2 = new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, getNext2Landmarks(passedQuest));
        listView2.setAdapter(adapter2);
        */

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    //TODO geofence should be implemented and change the passedQuest(in this class) and update it in the database(+ should change progress)





    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    //TODO: below gives error for missing persmission
    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        User user = helper.getUser(helper.getReadableDatabase());
        ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, getFirstLandmark(user.getActiveQuest()));
        listView.setAdapter(adapter);

        ArrayAdapter<Landmark> adapter2 = new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, getNext2Landmarks(user.getActiveQuest()));
        listView2.setAdapter(adapter2);

        if(mGeofenceList.isEmpty()){
            addGeofence(this.nextLandmark);
        }else{
            mGeofenceList.clear(); //TODO might be doing too many work?? also clearing everythings so only 1 geofence could be used at the time
            addGeofence(this.nextLandmark);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();


        LocationServices.GeofencingApi.removeGeofences(
                mGoogleApiClient,
                // This is the same pending intent that was used in addGeofences().
                getGeofencePendingIntent()
        ).setResultCallback(this); // Result processed in onResult().
    }


    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransistionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private void addGeofence(Landmark l) {
        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence. Each landmark has its own id so the geofence id's will be similair to the landmark id's
                .setRequestId(String.valueOf(l.getID()))

                .setCircularRegion(
                        l.getLocation().latitude,
                        l.getLocation().longitude,
                        Constants.GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());



    }

    public void deleteGeofence(Landmark l){
        for(Geofence fence :mGeofenceList) {
            if(fence.getRequestId() == String.valueOf(l.getID())) {
                mGeofenceList.remove(fence);
            }
        }
    }


    private Landmark[] getFirstLandmark(Quest q){ //set current Landmark and return an array with that 1 element
        Landmark[] nextLandmarks = new Landmark[1];
        this.nextLandmark = q.getLandmarks().get(0);
        nextLandmarks[0] = this.nextLandmark;
        return nextLandmarks;
    }

    private ArrayList<Landmark> getNext2Landmarks(Quest q){ //return an array with 2 Landmark after the first Landmark
        ArrayList<Landmark> next2Landmarks = new ArrayList<>();
        switch (q.getLandmarks().size()) {
            case 3:
                next2Landmarks.add(q.getLandmarks().get(2));
            case 2:
                next2Landmarks.add(q.getLandmarks().get(1));
                break;
        }
        return next2Landmarks;
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);
        Log.i("CONNECTION", "Connection to GoogleApiClient");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("CONNECTION", "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("CONNECTION", "Connection failed");
    }

    @Override
    public void onResult(Status status) { //TODO needs to do something still

    }

}
