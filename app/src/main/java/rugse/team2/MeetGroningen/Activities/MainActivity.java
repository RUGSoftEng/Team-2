package rugse.team2.MeetGroningen.Activities;

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
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.parse.Parse;

import rugse.team2.MeetGroningen.DatabaseStuff.DatabaseHelper;
import rugse.team2.MeetGroningen.DatabaseStuff.GlobalVariables;
import rugse.team2.MeetGroningen.DatabaseStuff.Initializer;
import rugse.team2.MeetGroningen.Objects.Landmark;
import rugse.team2.MeetGroningen.Objects.Quest;
import rugse.team2.MeetGroningen.Objects.User;

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
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(rugse.team2.MeetGroningen.R.id.toolbar);
        setSupportActionBar(toolbar);

        //for the first time of startup initialise stuff by looking for pref file (so could be affected by previous tries, wipe data to be sure)
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //Initializing the Parse server
        if (!(((GlobalVariables) this.getApplication()).getParseInitialized())){
            Parse.initialize(new Parse.Configuration.Builder(ctx)
                    .applicationId("htcAppId")
                    .clientKey(null)
                    .server("http://harrie.lutztec.nl/parse/")
                    .build()
            );
            ((GlobalVariables) this.getApplication()).setParseInitialized(true);
        }
        //end server initialization

        if (settings.getBoolean("first_time", true)) {

            DatabaseHelper db = new DatabaseHelper(ctx);

            //all landmarks and quests are put in the database
            Initializer i = new Initializer();

            ArrayList<Landmark> standardLandMarks = i.createStandardLandmarks();
            for(Landmark l : standardLandMarks){
                db.putInDatabase(db, l);
                //puts all initialized landmarks on the server(which isn't really needed) TODO: create sensible way to organize this
                //l.putOnServer();
            }

            ArrayList<Quest> standardQuests = i.createStandardQuests();
            for(Quest q : standardQuests){
                db.putInDatabase(db, q);
                //puts all initialized quests on the server(which isn't really needed) TODO: create sensible way to organize this
                //q.putOnServer();
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

        } else {
            Log.d("Comments", "not first time starting up");
        }


        //buttons
        Button pickQuest = (Button) findViewById(rugse.team2.MeetGroningen.R.id.newQuestButton);
        assert pickQuest != null;
        pickQuest.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //TODO change the strings back to the ones in the strings file as soon as we move the Make Landmark button somewhere else

                builder.setNeutralButton(getResources().getString(rugse.team2.MeetGroningen.R.string.ownQuestButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getBaseContext(), MakeQuestActivity.class);
                        startActivity(i);
                    }
                });

                builder.setPositiveButton(getResources().getString(rugse.team2.MeetGroningen.R.string.existingQuestButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getBaseContext(), ChooseQuestActivity.class);
                        startActivity(i);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button mapButton = (Button) findViewById(rugse.team2.MeetGroningen.R.id.mapButton);
        if (mapButton != null) {
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), MapActivity.class);
                    startActivity(i);
                }
            });
        }

        Button userpageButton = (Button) findViewById(rugse.team2.MeetGroningen.R.id.userpageButton_main);
        if (userpageButton != null) {
            userpageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), UserPageActivity.class);
                    startActivity(i);
                }
            });
        }

        Button continueButton = (Button) findViewById(rugse.team2.MeetGroningen.R.id.continueButton);
        if (continueButton != null) {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), ContinueQuestActivity.class));
                }
            });
        }

        Button tutorialButton = (Button) findViewById(rugse.team2.MeetGroningen.R.id.tutorialButton);
        if (tutorialButton != null){
            tutorialButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    startActivity(new Intent(getBaseContext(), TutorialActivity.class));
                }
            });
        }


        //make image switcher to switch background

        imgs.add(rugse.team2.MeetGroningen.R.mipmap.main_background1);
        imgs.add(rugse.team2.MeetGroningen.R.mipmap.main_background2);
        imgs.add(rugse.team2.MeetGroningen.R.mipmap.main_background3);


        imageSwitcher = (ImageSwitcher) findViewById(rugse.team2.MeetGroningen.R.id.imageSwitcher1);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                return new ImageView(getApplicationContext());
            }
        });
        //should always have an element
        imageSwitcher.postDelayed(new Runnable() {
            int i = 0;

            public void run() {
                RelativeLayout rLayout = (RelativeLayout) findViewById(rugse.team2.MeetGroningen.R.id.layout);
                if (rLayout != null) {
                    rLayout.setBackground(ResourcesCompat.getDrawable(getResources(), imgs.get(i), null));
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
        getMenuInflater().inflate(rugse.team2.MeetGroningen.R.menu.menu_main, menu);
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

        switch (item.getItemId()) {
            case rugse.team2.MeetGroningen.R.id.settings:

                return true;
            case rugse.team2.MeetGroningen.R.id.help:
                Intent tutorial = new Intent(getBaseContext(), TutorialActivity.class);
                startActivity(tutorial);
                return true;
            case rugse.team2.MeetGroningen.R.id.manage_custom:
                Intent i = new Intent(getBaseContext(), ManageCustomActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
