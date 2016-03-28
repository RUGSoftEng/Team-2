package com.mycompany.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by Ruben on 17/03/2016.
 */
public class OnQuestActivity extends AppCompatActivity {

    private ProgressBar mProgress;
    private final static int TOTAL_PROGRESS_TIME = 100;
    private Quest passedQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onquest);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");

        Log.d("TEST", passedQuest.toString());

        Button b = (Button) findViewById(R.id.button); //TODO instead of button this should be activated by geofences,(landmarkPopup should be merged with questActivity)
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LandMarkPopUp.class));
            }
        });


        //Progress of quest
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        if(passedQuest.getProgress() < TOTAL_PROGRESS_TIME){
            mProgress.setProgress(passedQuest.getProgress());
        }

        Landmark[] nextLandmark = new Landmark[1];
        nextLandmark[0] = passedQuest.getLandmarks().get(0);

        ListView listView = (ListView) findViewById(R.id.listView3);
        ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, nextLandmark);
        listView.setAdapter(adapter);


        ArrayList<Landmark> next2Landmarks = new ArrayList<>();
        switch (passedQuest.getLandmarks().size()) {
            case 3:
                next2Landmarks.add(passedQuest.getLandmarks().get(2));
            case 2:
                next2Landmarks.add(passedQuest.getLandmarks().get(1));
                break;
        }



        ListView listView2 = (ListView) findViewById(R.id.listView4);
        ArrayAdapter<Landmark> adapter2 = new ArrayAdapter<Landmark>(this, android.R.layout.simple_list_item_1, next2Landmarks);
        listView2.setAdapter(adapter2);


    }

    //TODO geofence should be implemented and change the passedQuest(in this class) and update it in the database(+ should change progress)


    @Override
    protected void onResume(){
        super.onResume();
    }

    /*
    //redirect to quest content page, passing the chosen quest
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(getBaseContext(), QuestActivity.class);
            i.putExtra("PassedQuest", passedQuest);
            startActivity(i);
            //Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
            //        Toast.LENGTH_SHORT).show();
        }
    });
    */




}
