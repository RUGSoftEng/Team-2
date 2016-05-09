package com.mycompany.myapp;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Non database related constants.
 *
 * Created by Ruben on 29/03/2016.
 */
public class Constants {



    public static final int TOTAL_QUEST_PROGRESS = 100; //Max possible quest progress
    public static final LatLng COORDINATE_GRONINGEN = new LatLng(53.2194, 6.5665);;


    //geofence related stuff
    public static final String TAG = "GeofenceTransitionsIS"; //TAG for geofence
    public static final String FINISHED_QUEST_TEXT = "Congratulations! You finished this quest! Good job!"; //Text shown when finishing a quest

    public static final String COMPLETED_LANDMARK_TEXT = "Landmark visited";
    public static final int FINISHED_LANDMARK_DURATION = Toast.LENGTH_SHORT;

    public static final int MAXIMAL_ACTIVATION_DISTANCE = 20; //distance to landmark before its completed


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 10; //Random number to give a permission code

    public static final int PADDING = 100; //Padding of Map and markers
    public static final int MILLISEC = 1000; //second converted to millisecs



}
