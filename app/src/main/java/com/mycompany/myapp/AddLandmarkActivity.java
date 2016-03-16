package com.mycompany.myapp;

import android.Manifest;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruben on 28/02/2016.
 */

//TODO: this class should be linked to the button for adding a landmark to your quest
    //TODO: move the permission question to another better suited location
public class AddLandmarkActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_ID = 10;

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


        //take all landmark objects from the database and put them into a listView
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<Landmark> a = getAllLandmarks(db);
        Landmark[] landmarks = a.toArray(new Landmark[a.size()]);


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
        //Ask for persimission in below Permissions, and assign code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
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

    public ArrayList<Landmark> getAllLandmarks(SQLiteDatabase db) {
        ArrayList<Landmark> list = new ArrayList<Landmark>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBConstants.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ByteArrayInputStream bis = new ByteArrayInputStream(cursor.getBlob(1));
                ObjectInput in = null;
                try {
                    in = new ObjectInputStream(bis);
                    Landmark landmark = (Landmark) in.readObject();
                    list.add(landmark);


                    in.close();
                    bis.close();
                } catch (IOException e) {
                    Log.e("IOException", "failed to create input stream for landmark");
                } catch (ClassNotFoundException ex){
                    Log.e("ClassNotFound", "failed to find class while creating landmark");
                }

                } while (cursor.moveToNext());
        }
        return list;
    }
}

