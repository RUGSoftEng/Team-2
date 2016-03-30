package com.mycompany.myapp;

/**
 * Created by Ruben on 29/03/2016.
 */
//TODO: check and improve still

import android.app.IntentService;

    import android.content.Context;
    import android.content.Intent;

    import android.text.TextUtils;
    import android.util.Log;
    import android.widget.Toast;

    import com.google.android.gms.location.Geofence;
    import com.google.android.gms.location.GeofencingEvent;

    import java.util.ArrayList;
    import java.util.List;

    /**
     * Listener for geofence transition changes.
     *
     * Receives geofence transition events from Location Services in the form of an Intent containing
     * the transition type and geofence id(s) that triggered the transition. //TODO make it remove and add landmarks
     */
    public class GeofenceTransistionsIntentService extends IntentService {



        /**
         * This constructor is required, and calls the super IntentService(String)
         * constructor with the name for a worker thread.
         */
        public GeofenceTransistionsIntentService() {
            // Use the TAG to name the worker thread.
            super(Constants.TAG);
        }

        @Override
        public void onCreate() {
            super.onCreate();
        }

        /**
         * Handles incoming intents.
         * @param intent sent by Location Services. This Intent is provided to Location
         *               Services (inside a PendingIntent) when addGeofences() is called.
         */
        @Override
        protected void onHandleIntent(Intent intent) {
            GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
            if (geofencingEvent.hasError()) {
                Log.e(Constants.TAG, "Handling intent went wrong");
                return;
            }

            // Get the transition type.
            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            // Test that the reported transition was entering an geofence.
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

                // Get the geofences that were triggered. A single event can trigger multiple geofences.
                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

                // Get the transition details as a String.
                String geofenceTransitionDetails = getGeofenceTransitionDetails(
                        this,
                        geofenceTransition,
                        triggeringGeofences
                );

                // Send notification and log the transition details. visit landmark
                visitLandmark(triggeringGeofences);

                Log.i(Constants.TAG, geofenceTransitionDetails);
            } else {
                // Log the error.
                Log.e(Constants.TAG, "invalid geofence type");
            }
        }


        private void visitLandmark(List <Geofence> triggeringGeofences){ //will change progress in user active quest
            DatabaseHelper helper = new DatabaseHelper(getBaseContext());
            User user = helper.getUser(helper.getReadableDatabase());
            Quest activeQuest = user.getActiveQuest();

            for(Geofence fence : triggeringGeofences) {
                activeQuest.isCompleted(Integer.getInteger(fence.getRequestId()));
            }

            Log.d("GEOFENCE", "finished a landmark by visiting");
            Toast toast;
            if(activeQuest.getProgress() == 100){ //user finished the quest
                user.finishQuest(activeQuest);
                toast = Toast.makeText(getBaseContext(), Constants.FINISHED_QUEST_TEXT, Constants.FINISHED_QUEST_DURATION);
                toast.show();
            }else{
                toast = Toast.makeText(getBaseContext(), Constants.COMPLETED_LANDMARK_TEXT, Constants.FINISHED_LANDMARK_DURATION);
                toast.show();
            }

            helper.updateInDatabase(helper, user);
            helper.close();

            //TODO: on trigger should start new geofence and remove old geofence

        }

        /**
         * Gets transition details and returns them as a formatted string.
         *
         * @param context               The app context.
         * @param geofenceTransition    The ID of the geofence transition.
         * @param triggeringGeofences   The geofence(s) triggered.
         * @return                      The transition details formatted as String.
         */
        private String getGeofenceTransitionDetails(
                Context context,
                int geofenceTransition,
                List<Geofence> triggeringGeofences) {

            String geofenceTransitionString = getTransitionString(geofenceTransition);

            // Get the Ids of each geofence that was triggered.
            ArrayList triggeringGeofencesIdsList = new ArrayList();
            for (Geofence geofence : triggeringGeofences) {
                triggeringGeofencesIdsList.add(geofence.getRequestId());
            }
            String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);

            return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
        }


        /**
         * Maps geofence transition types to their human-readable equivalents.
         *
         * @param transitionType    A transition type constant defined in Geofence
         * @return                  A String indicating the type of transition
         */
        private String getTransitionString(int transitionType) {
            switch (transitionType) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    return "geofence_transition_entered";
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    return "geofence_transition_exited";
                default:
                    return "unknown_geofence_transition";
            }
        }

    }
