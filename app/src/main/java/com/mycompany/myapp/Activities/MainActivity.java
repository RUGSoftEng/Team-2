package com.mycompany.myapp.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.DatabaseStuff.Initializer;
import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.R;
import com.mycompany.myapp.Objects.User;

import java.util.ArrayList;

/**
 * This class represents the main activity (initial Android window). Next to showing
 * different pictures of Groningen in the background, it offers buttons for starting a quest,
 * continuing a quest, showing the location of all available landmarks on a map, and viewing
 * the current user's profile page. It also initialises all standard quests and landmarks.
 */
public class MainActivity extends AppCompatActivity {


    private static int IMAGE_DELAY = 6000;
    private ImageSwitcher imageSwitcher; //the instance that switches the background image from time to time
    private ArrayList<Integer> imgs = new ArrayList<>(); //the list of images to be displayed consecutively
    private Context ctx = this; //the context of the application, which holds global information about its execution environment

    /* Initialises the activity as described above, and binds clicking the 'new quest', 'map', 'user page', and 'continue'
     * buttons to starting a new Alert Dialog, MapActivity, UserPageActivity, and ContinueQuestActivity, respectively.
     * Furthermore it handles image switching by having a runnable change the background every IMAGE_DELAY milliseconds. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //for the first time of startup initialise stuff by looking for pref file (so could be affected by previous tries, wipe data to be sure)
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("first_time", true)) {
            Log.d("Comments", "First time starting up");

            DatabaseHelper db = new DatabaseHelper(ctx);

            //all landmarks and quests are put in the database
            Initializer i = new Initializer();

            ArrayList<Landmark> standardLandMarks = i.createStandardLandmarks();
            for(Landmark l : standardLandMarks){
                db.putInDatabase(db, l);
            }

            ArrayList<Quest> standardQuests = i.createStandardQuests();
            for(Quest q : standardQuests){
                db.putInDatabase(db, q);
            }

            //initial user is put into the database
            User u = i.createStandardUser();
            db.putInDatabase(db, u);


            db.close(); //closing database connection

            //record the fact that the app has been started at least once
            settings.edit().putBoolean("first_time", false).apply();

            //request location permission if necessary
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET}, 10);
                }
            }

//            // Here, thisActivity is the current activity
//            if (ContextCompat.checkSelfPermission(thisActivity,
//                    Manifest.permission.READ_CONTACTS)
//                    != PackageManager.PERMISSION_GRANTED) {
//
//
//
//                    // No explanation needed, we can request the permission.
//
//                    ActivityCompat.requestPermissions(thisActivity,
//                            new String[]{Manifest.permission.READ_CONTACTS},
//                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//            }


        } else {
            Log.d("Comments", "not first time starting up");
        }


        //buttons
        Button pickQuest = (Button) findViewById(R.id.newQuestButton);
        assert pickQuest != null;
        pickQuest.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //TODO change the strings back to the ones in the strings file as soon as we move the Make Landmark button somewhere else

                builder.setNeutralButton("NEW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getBaseContext(), MakeQuestActivity.class);
                        startActivity(i);
                    }
                });

                builder.setPositiveButton("EXISTING", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getBaseContext(), ChooseQuestActivity.class);
                        startActivity(i);
                    }
                });

//                builder.setNegativeButton("LANDMARK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent i = new Intent(getBaseContext(), MakeLandmarkActivity.class);
//                        startActivity(i);
//                    }
//                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button mapButton = (Button) findViewById(R.id.mapButton);
        if (mapButton != null) {
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), MapActivity.class);
                    startActivity(i);
                }
            });
        }

        Button userpageButton = (Button) findViewById(R.id.userpageButton_main);
        if (userpageButton != null) {
            userpageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), UserPageActivity.class);
                    startActivity(i);
                }
            });
        }

        Button continueButton = (Button) findViewById(R.id.continueButton);
        if (continueButton != null) {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), ContinueQuestActivity.class));
                }
            });
        }


        //make image switcher to switch background
        //NOTE: For some reason martini.jpg doesn't work so there might be more pictures not working
        imgs.add(R.drawable.martini4);
        imgs.add(R.drawable.splashscreen5);
        imgs.add(R.drawable.splashscreen7);
        imgs.add(R.drawable.splashscreen6);
        imgs.add(R.drawable.splashscreen3);


        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                return new ImageView(getApplicationContext());
            }
        });
        //should always have an element, otherwise null pointer exception TODO: catch any exceptions/errors
        imageSwitcher.postDelayed(new Runnable() {
            int i = 0;

            public void run() {
                LinearLayout rLayout = (LinearLayout) findViewById(R.id.layout);
                if (rLayout != null) {
                    rLayout.setBackground(getResources().getDrawable(imgs.get(i)));
                }
                i++;
                if (i == imgs.size()) i = 0;
                imageSwitcher.postDelayed(this, IMAGE_DELAY);
            }
        }, IMAGE_DELAY);

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        imageSwitcher.setInAnimation(in);
    }

    /* Initialises the action bar. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Handles action bar clicks. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
