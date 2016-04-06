package com.mycompany.myapp;

/**
 * Created by Ruben on 23/03/2016.
 */

    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.support.v4.app.FragmentActivity;

    import com.google.android.gms.maps.CameraUpdate;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.SupportMapFragment;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.LatLngBounds;
    import com.google.android.gms.maps.model.Marker;
    import com.google.android.gms.maps.model.MarkerOptions;

    import java.util.ArrayList;
    import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        private static final LatLng COORDINATE_GRONINGEN = new LatLng(53.217717, 6.566458);
        private List<Marker> markers;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(COORDINATE_GRONINGEN));

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

            Landmark something = a.get(0);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(something.getLocation(), 12.0f));
        }
}
