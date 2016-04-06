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
    public static final String FINISHED_QUEST_TEXT = "Congratulations! You finished this quest! Good job!"; //Text shown when finishing a quest
    public static final int FINISHED_QUEST_DURATION = Toast.LENGTH_LONG; //time of above message, only Short or Long

    public static final String COMPLETED_LANDMARK_TEXT = "Landmark visited";
    public static final int FINISHED_LANDMARK_DURATION = Toast.LENGTH_SHORT;

    public static final int MAXIMAL_ACTIVATION_DISTANCE = 20; //distance to landmark before its completed


    public static final int PADDING = 100;
    public static final int MILLISEC = 1000;



}
