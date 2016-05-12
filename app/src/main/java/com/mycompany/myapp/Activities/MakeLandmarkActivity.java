package com.mycompany.myapp.Activities;

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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.myapp.Constants;
import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.R;

import java.util.UUID;


/**
 * Created by Ruben on 11/05/2016.
 */
public class MakeLandmarkActivity extends FragmentActivity {


    private GoogleMap mMap; //field description goes here
    private Landmark customLandmark;
    private LatLng landmarkPosition;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makelandmark);

        Button FINISH = (Button) findViewById(R.id.FinishButton);

        final EditText landmarkName = (EditText) findViewById(R.id.NameEditText);
        final EditText landmarkStory = (EditText) findViewById(R.id.StoryEditText);

        /*
         * Finish button listener starts a Dialog, that asks for a questName, the returning result is caught by the
         * onDialogPositiveClick(Dialog dialog) and onDialogNegativeClick(Dialog dialog) methods.
         */
        FINISH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(landmarkName.getText().toString().equals("") || landmarkPosition != null) {

                    customLandmark = new Landmark(landmarkName.getText().toString(), UUID.randomUUID().toString() );
                    customLandmark.setLocation(landmarkPosition);
                    customLandmark.setInformation(landmarkStory.getText().toString());


                    DatabaseHelper helper = new DatabaseHelper(getBaseContext());
                    helper.putInDatabase(helper, customLandmark);

                    Toast.makeText(getApplicationContext(),
                            R.string.customAddedTextToast, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),
                            R.string.forgotToast, Toast.LENGTH_LONG).show();
                }
            }
        });

        setUpMapIfNeeded();


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(marker != null) {
                    marker.remove();
                }
                landmarkPosition = latLng;
                marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        });

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
        //do a null check to confirm that we have not already instantiated the map
        if (mMap == null) {
            //try to obtain the map from the SupportMapFragment
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            //check if we were successful in obtaining the map
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap(){
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(Constants.COORDINATE_GRONINGEN, Constants.NORMAL_ZOOM);
        mMap.animateCamera(cu);
    }
}
