package com.mycompany.myapp;

import android.widget.Toast;

/**
 * Created by Ruben on 29/03/2016.
 */
//Non database related constants
public class Constants {



    public static final int TOTAL_QUEST_PROGRESS = 100; //Max possible quest progress


    //geofence related stuff
    public static final String TAG = "GeofenceTransitionsIS"; //TAG for geofence
    public static final int GEOFENCE_RADIUS_IN_METERS = 80; //radius of geofence
    public static final String FINISHED_QUEST_TEXT = "Well Done! You finished the quest"; //Text shown when finishing a quest
    public static final int FINISHED_QUEST_DURATION = Toast.LENGTH_LONG; //time of above message, only Short or Long
    public static final int GEOFENCE_EXPIRATION_IN_HOURS = 12; //stops tracking geofence after 12 hours
    public static final int GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000; //the expiration converted to milliseconds

    public static final String COMPLETED_LANDMARK_TEXT = "Landmark visited";
    public static final int FINISHED_LANDMARK_DURATION = Toast.LENGTH_SHORT;

}
