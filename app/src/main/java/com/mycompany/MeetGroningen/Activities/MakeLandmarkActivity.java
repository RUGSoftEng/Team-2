package com.mycompany.MeetGroningen.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.MeetGroningen.Constants;
import com.mycompany.MeetGroningen.DatabaseStuff.DatabaseHelper;
import com.mycompany.MeetGroningen.Objects.Landmark;
import com.mycompany.MeetGroningen.R;

import java.util.UUID;


/**
 * Created by Ruben on 11/05/2016.
 */
public class MakeLandmarkActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap; //field description goes here
    private Landmark customLandmark;
    private LatLng landmarkPosition = null;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makelandmark);

        Button FINISH = (Button) findViewById(R.id.FinishButton);

        final EditText landmarkName = (EditText) findViewById(R.id.NameEditText);
        final EditText landmarkStory = (EditText) findViewById(R.id.StoryEditText);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /*
         * Finish button listener starts a Dialog, that asks for a questName, the returning result is caught by the
         * onDialogPositiveClick(Dialog dialog) and onDialogNegativeClick(Dialog dialog) methods.
         */
        FINISH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(landmarkName.getText().toString().equals("")) && (landmarkPosition != null)) {
                    customLandmark = new Landmark(landmarkName.getText().toString(), UUID.randomUUID().toString());
                    customLandmark.setLocation(landmarkPosition);
                    customLandmark.setInformation(landmarkStory.getText().toString());
                    customLandmark.setUserGenerated(true);

                    DatabaseHelper helper = new DatabaseHelper(getBaseContext());
                    helper.putInDatabase(helper, customLandmark);

                    Toast.makeText(getApplicationContext(),
                            R.string.customAddedTextToast, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getBaseContext(), MakeQuestActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.forgotToast, Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap map) {
        //get the locations of the landmarks in this quest
        mMap = map;
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(Constants.COORDINATE_GRONINGEN, Constants.NORMAL_ZOOM);
        mMap.animateCamera(cu);
        mMap.setMyLocationEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }
                landmarkPosition = latLng;
                marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        });
    }
}
