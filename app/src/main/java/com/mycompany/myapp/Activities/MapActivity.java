package com.mycompany.myapp.Activities;

    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.support.v4.app.FragmentActivity;

    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.SupportMapFragment;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.Marker;
    import com.google.android.gms.maps.model.MarkerOptions;
    import com.mycompany.myapp.Constants;
    import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
    import com.mycompany.myapp.Objects.Landmark;
    import com.mycompany.myapp.R;

    import java.util.ArrayList;
    import java.util.List;

/**
 * Class description goes here.
 *
 * Created by Ruben on 23/03/2016.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap; //field description goes here
        private List<Marker> markers; //field description goes here

        /* Method description goes here. */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        /* Method description goes here. */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Constants.COORDINATE_GRONINGEN));

            //take all landmark objects from the database and put them into a listView
            DatabaseHelper helper = new DatabaseHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();

            ArrayList<Landmark> a = helper.getAllLandmarks(db);
            markers = new ArrayList<>();
            Marker testmark;
            for (Landmark landmark : a) {
               testmark = mMap.addMarker(new MarkerOptions()
                        .position(landmark.getLocation())
                        .title(landmark.getName()));
                markers.add(testmark);
            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Constants.COORDINATE_GRONINGEN, 12.0f));
        }
}
