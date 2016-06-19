package rugse.team2.MeetGroningen.Activities;

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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import rugse.team2.MeetGroningen.Constants;
import rugse.team2.MeetGroningen.DatabaseStuff.DatabaseHelper;
import rugse.team2.MeetGroningen.Objects.Landmark;

import java.util.UUID;


/**
 * This class represents the activity (Android window) for creating one's own landmark.
 * It presents a form where one can fill in the custom landmark's name and description,
 * and above it a map which can be long pressed to change its position. A marker is
 * shown to indicate the chosen position, and clicking a button for finishing the
 * landmark creation process stores the new landmark in the database if it is valid.
 *
 * Created by Ruben on 11-05-2016.
 */
public class MakeLandmarkActivity extends FragmentActivity implements OnMapReadyCallback {
    /** The (Google) map. */
    private GoogleMap mMap;
    /** The new custom landmark. */
    private Landmark customLandmark;
    /** The new landmark's tentative position. */
    private LatLng landmarkPosition = null;
    /** The marker on the map showing the new landmark's tentative position. */
    private Marker marker;

    /**
     * Initialises the activity as described above, and binds clicking the 'finish' button
     * to checking whether a landmark name and position have been specified, and either storing
     * the custom landmark in the database if this is the case or showing an error message otherwise.
     *
     * @param savedInstanceState If the activity is being re-initialised after previously being shut down, then this Bundle
     *                           contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_makelandmark);

        Button FINISH = (Button) findViewById(rugse.team2.MeetGroningen.R.id.FinishButton);

        final EditText landmarkName = (EditText) findViewById(rugse.team2.MeetGroningen.R.id.NameEditText);
        final EditText landmarkStory = (EditText) findViewById(rugse.team2.MeetGroningen.R.id.StoryEditText);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(rugse.team2.MeetGroningen.R.id.map);
        mapFragment.getMapAsync(this);


        /*
         * Finish button listener starts a Dialog, that asks for a landmark name. The returning result is then
         * caught by the onDialogPositiveClick(Dialog dialog) and onDialogNegativeClick(Dialog dialog) methods.
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
                            rugse.team2.MeetGroningen.R.string.customAddedTextToast, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getBaseContext(), MakeQuestActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),
                            rugse.team2.MeetGroningen.R.string.forgotToast, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * Saves the map for further use. This is called automatically after the map has been prepared and is ready for use.
     * Also handles long map presses by updating the new landmark's tentative position to the latest map position pressed.
     *
     * @param map The (Google) map which has been prepared asynchronously and is now ready for use.
     */
    @Override
    public void onMapReady(GoogleMap map) {
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
