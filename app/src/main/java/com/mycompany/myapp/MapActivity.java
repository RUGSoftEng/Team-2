package com.mycompany.myapp;

/**
 * Created by Ruben on 23/03/2016.
 */

    import android.os.Bundle;
    import android.support.v4.app.FragmentActivity;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.SupportMapFragment;
    import com.google.android.gms.maps.model.LatLng;

    public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        private static final LatLng COORDINATE_GRONINGEN = new LatLng(53.217717, 6.566458);

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
        }
}
