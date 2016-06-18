package rugse.team2.MeetGroningen;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Non database related constants.
 *
 * Created by Ruben on 29-03-2016.
 */
public class Constants {



    public static final int TOTAL_QUEST_PROGRESS = 100; //maximum possible quest progress
    public static final LatLng COORDINATE_GRONINGEN = new LatLng(53.2194, 6.5665);;


    //geofencing related stuff
    public static final String TAG = "GeofenceTransitionsIS"; //TAG for geofence
    public static final String FINISHED_QUEST_TEXT = "Congratulations! You finished this quest! Good job!"; //text shown upon finishing a quest

    public static final String COMPLETED_LANDMARK_TEXT = "Landmark visited";
    public static final int FINISHED_LANDMARK_DURATION = Toast.LENGTH_SHORT;

    public static final int MAXIMAL_ACTIVATION_DISTANCE = 20; //required distance to a landmark before it is seen as reached

    public static final int MINIMAL_GPS_DISTANCE = 5; //required distance in meters for gps to start checking
    public static final int MINIMAL_GPS_TIME = 10; //required time in milliseconds for gps to start checking

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 10; //random number to give a permission code

    public static final int PADDING = 100; //padding of maps and markers
    public static final int MILLISEC = 1000; //second converted to milliseconds

    public static final int NORMAL_ZOOM = 10;


}
