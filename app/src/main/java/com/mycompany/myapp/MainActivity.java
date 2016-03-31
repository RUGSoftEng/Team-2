package com.mycompany.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


//Except for being the main screen, mainActivity.java also initializes all standard quests and landmarks
public class MainActivity extends AppCompatActivity { //TODO: apps should still ask for permission so the user doesn't have to manually give permission


    private static int IMAGE_DELAY = 6000;
    private ImageSwitcher imageSwitcher;
    private ArrayList<Integer> imgs = new ArrayList<Integer>();
    private Context ctx = this;
    private Button continueButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //For the first time of startup(initialize stuff) by looking for pref file(so could be affected by previous tries, wipe data to be sure)
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


            db.close(); // Closing database connection

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("first_time", false).commit();
        } else {
            Log.d("Comments", "not first time starting up");
        }


        //Buttons
        Button pickQuest = (Button) findViewById(R.id.button_main);
        pickQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ChooseQuestActivity.class);
                startActivity(i);
            }
        });


        Button makeQuest = (Button) findViewById(R.id.createQuest);
        makeQuest.setVisibility(View.GONE);
        makeQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddLandmarkActivity.class);
                startActivity(i);
            }
        });

        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MapActivity.class);
                startActivity(i);
            }
        });

        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ContinueQuestActivity.class));
            }
        });




        //make image switcher to switch background
        //NOTE: For some reason martini.jpg doesn't work so their might be pictures not working
        imgs.add(R.drawable.martini4);
        imgs.add(R.drawable.splashscreen5);
        imgs.add(R.drawable.splashscreen7);
        imgs.add(R.drawable.splashscreen6);
        imgs.add(R.drawable.splashscreen3);


        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                return myView;
            }
        });
        //should always have a element otherwise null pointer exception TODO: catch any exceptions/errors
        imageSwitcher.postDelayed(new Runnable() {
            int i = 0;

            public void run() {
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.layout);
                rLayout.setBackground(getResources().getDrawable(imgs.get(i)));
                i++;
                if (i == imgs.size()) i = 0;
                imageSwitcher.postDelayed(this, IMAGE_DELAY);
            }
        }, IMAGE_DELAY);

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        imageSwitcher.setInAnimation(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
