package com.mycompany.myapp;

import android.Manifest;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ruben on 28/02/2016.
 */

//TODO: this class should be linked to the button for adding a landmark to your quest
public class AddLandmarkActivity extends AppCompatActivity {

    Button ADD, SELECT;
    private Button LOC;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_landmark);
        ADD = (Button) findViewById(R.id.addButton);
        LOC = (Button) findViewById(R.id.locButton);
        textView = (TextView) findViewById(R.id.textView);
        ListView listView = (ListView) findViewById(R.id.listView);


        //TODO: now hardcoded, has to be done for every Landmark in the database, binded to arrayAdapter to print each value of the landmark

        /* TODO: If not using server, below code should be done once on the first time using the app only, putting landmarks in database
        DatabaseHelper db = new DatabaseHelper(ctx);
        SQLiteDatabase database = db.getWritableDatabase();
        db.insert(); TODO: insert landmarks here
        db.close(); // Closing database connection
        */

        Landmark martiniToren = new Landmark("Martini Toren", 1);
        martiniToren.setLocation(new LatLng(53.219383, 6.568125));

        Landmark aKerk = new Landmark("A Kerk", 2);
        aKerk.setLocation(new LatLng(53.216498, 6.562386));


        Landmark[] landmarks = {
                martiniToren,
                aKerk,
        };


        ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this,
                android.R.layout.simple_list_item_1, landmarks);

        listView.setAdapter(adapter);



        //SELECT = (Button) findViewById(R.id."copy appropriate id of button here(xml)"); TODO: add button from layout here
        /*SELECT.setOnClickListener(new OnClickListener(){ TODO: add here button listiner and what to do (select in this case)

            @Override
            public void onClick(View v){

            }

        });
        */

        ADD.setOnClickListener(new View.OnClickListener() { //TODO: add here button listiner and what to do (add selected Landmark in this case)

                                   @Override
                                   public void onClick(View v) {


                                   }
                               });


            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //if location has changed print longitude and latitude
                textView.append("\n Latitude = " +location.getLatitude() +"\n Longitude = "+location.getLongitude());

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {
            configureButton();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }


    private void configureButton() {
        LOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uses as input GPS, checks every 5000 miliseconds, if more than 25 meters difference
                //TODO: remove permission error (permissions are checked already)
                locationManager.requestLocationUpdates("gps", 5000, 25, locationListener);

            }
        });
    }
}

