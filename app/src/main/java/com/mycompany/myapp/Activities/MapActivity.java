package com.mycompany.myapp.Activities;

    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.support.v4.app.FragmentActivity;

    import com.google.android.gms.maps.CameraUpdate;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.MapFragment;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.model.LatLngBounds;
    import com.google.android.gms.maps.model.Marker;
    import com.google.android.gms.maps.model.MarkerOptions;
    import com.mycompany.myapp.Constants;
    import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
    import com.mycompany.myapp.Objects.Landmark;
    import com.mycompany.myapp.R;

    import java.util.ArrayList;

/**
 * This class represents the activity (Android window) for showing all landmarks.
 * It gets the landmarks from the database and displays them as markers in a map.
 *
 * Created by Ruben on 23-03-2016.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    /* Initialises the activity as described above by preparing the map and calling onMapReady when it is finished. */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);

            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        /* Queries the database for all available landmarks, converts their locations
         * into map markers, and moves the map camera to view them all simultaneously. */
        @Override
        public void onMapReady(GoogleMap mMap) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Constants.COORDINATE_GRONINGEN));

            //take all landmark objects from the database and put them into a ListView
            DatabaseHelper helper = new DatabaseHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();

            ArrayList<Landmark> a = helper.getAllLandmarks(db);
            Marker testmark;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Landmark landmark : a) {
                testmark = mMap.addMarker(new MarkerOptions()
                        .position(landmark.getLocation())
                        .title(landmark.getName()));

                builder.include(testmark.getPosition());
            }
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, Constants.PADDING);
                mMap.animateCamera(cu);
        }
}
