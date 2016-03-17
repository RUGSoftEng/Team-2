package com.mycompany.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ruben on 17/03/2016.
 */
public class OnQuestActivity extends AppCompatActivity {













    /*
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView textView;

    private static final int LOCATION_PERMISSION_ID = 10;

    private Button LOC;

    //Below asks for persmission and get location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questexplanation);
        LOC = (Button) findViewById(R.id.locButton);
        textView = (TextView) findViewById(R.id.textView);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //if location has changed print longitude and latitude
                textView.append("\n Latitude = " + location.getLatitude() + "\n Longitude = " + location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }


            @Override
            public void onProviderDisabled(String provider) {
                //if GPS disabled ask user for enabling
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };
        //Ask for persimission in below Permissions, and assign code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, LOCATION_PERMISSION_ID);
                return;
            }
        } else {
            configureButton();
        }
    }

    //check the persimissions with given Persmission code
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }


    private void configureButton() {
        LOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uses as input GPS, checks every 1000 miliseconds, if more than 0 meters difference
                //TODO: remove permission error (permissions are checked already)
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

            }
        });
    }
    */
}
