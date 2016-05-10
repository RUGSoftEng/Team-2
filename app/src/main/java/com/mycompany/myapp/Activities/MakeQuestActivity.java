package com.mycompany.myapp.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.myapp.Constants;
import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Dialog.AskQuestNameDialog;
import com.mycompany.myapp.Objects.ExactQuest;
import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.User;
import com.mycompany.myapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class description goes here.
 *
 * Created by Ruben on 28/02/2016.
 */
public class MakeQuestActivity extends FragmentActivity implements AskQuestNameDialog.QuestNameDialogListener {

    private Button FINISH;

    private GoogleMap mMap; //field description goes here

    private ArrayList<Landmark> landmarks; //field description goes here
    private ArrayList<Landmark> selectedLandmarks; //field description goes here

    private List<Marker> markers; //field description goes here

    /* Method description goes here. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makequest);
        FINISH = (Button) findViewById(R.id.FinishButton);
        final ListView chooseLandmarkListView = (ListView) findViewById(R.id.chooseLandmarkList);
        final ListView inQuestListView = (ListView) findViewById(R.id.InQuestList);

        //take all landmark objects from the database and put them into a listView
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        landmarks = helper.getAllLandmarks(db);
        selectedLandmarks = new ArrayList<Landmark>();

        final ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this,
                android.R.layout.simple_list_item_1, landmarks);

        chooseLandmarkListView.setAdapter(adapter);

        final ArrayAdapter<Landmark> adapter2 = new ArrayAdapter<Landmark>(this,
                android.R.layout.simple_list_item_1, selectedLandmarks);

        inQuestListView.setAdapter(adapter2);

        try {
            chooseLandmarkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Landmark selectedLandmark = (Landmark) parent.getAdapter().getItem(position);
                    selectedLandmarks.add(selectedLandmark);
                    landmarks.remove(selectedLandmark);
                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    for (Marker marker : markers) {
                        if (marker.getTitle().equals(selectedLandmark.getName())) {
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        }
                    }
                }
            });
        }catch(NullPointerException e){
            Log.e("ListItem Error", "A list item is null: " + e);
        }

        try {
            inQuestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Landmark selectedLandmark = (Landmark) parent.getAdapter().getItem(position);
                    selectedLandmarks.remove(selectedLandmark);
                    landmarks.add(selectedLandmark);
                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    for (Marker marker : markers) {
                        if (marker.getTitle().equals(selectedLandmark.getName())) {
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        }
                    }
                }
            });
        }catch(NullPointerException e){
            Log.e("ListItem Error", "A list item is null: " + e);
        }


        /*
        Finish button listener starts a Dialog, that asks for a questName, the returning result is catched by the
        onDialogPositiveClick(Dialog dialog) and onDialogNegativeClick(Dialog dialog) methods.
         */
        FINISH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AskQuestNameDialog dialog = new AskQuestNameDialog();
                dialog.show(getSupportFragmentManager(), "dialog");

            }
        });


        setUpMapIfNeeded();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {


            public boolean onMarkerClick(Marker marker) {
                for (Landmark landmark : landmarks) {
                    if (marker.getTitle().equals(landmark.getName())) {
                        selectedLandmarks.add(landmark);
                        landmarks.remove(landmark);
                        adapter.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        break;
                    }
                }
                return false;
            }
        });

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, Constants.PADDING);
                mMap.animateCamera(cu);
            }
        });


        db.close();
        helper.close();
    }



    /* The dialog fragment receives a reference to this Activity through the
     * Fragment.onAttach() callback, which it uses to call the following methods
     * defined by the AskQuestNameDialog.QuestNameDialogListener interface. */
    @Override
    public void onDialogPositiveClick(AskQuestNameDialog dialog) {
        // User touched the dialog's positive button, a new quest is created and added to the User
        ExactQuest quest = new ExactQuest(UUID.randomUUID().toString(), dialog.getQuestName(), true); //TODO: Still hardcoded name
        quest.addLandmarkList(selectedLandmarks);

        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        User user = helper.getUser(helper.getReadableDatabase());
        user.addQuest(quest);
        helper.updateInDatabase(helper, user);

        helper.close();

        Intent i = new Intent(getBaseContext(), ContinueQuestActivity.class);
        startActivity(i);
    }

    /* Method description goes here. */
    @Override
    public void onDialogNegativeClick(AskQuestNameDialog dialog) {
        // User touched the dialog's negative button, nothing happens

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
        DatabaseHelper helper = new DatabaseHelper(this);
        for (Landmark landmark : helper.getAllLandmarks(helper.getReadableDatabase())) {
            testmark = mMap.addMarker(new MarkerOptions()
                    .position(landmark.getLocation())
                    .title(landmark.getName()));
            markers.add(testmark);
        }
    }
}
