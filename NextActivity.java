package com.example.ruben.softwareengeneering20;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//TODO: Still largly hardcoded
public class NextActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;   // later on cursoradapter
    String[] quests = {"Pub quest",  "Cultural quest", "Food quest"}; // need to come from database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quests);
        listView.setAdapter(adapter);


        //Start a new quest and being able to run it(now hard coded for MVP)
        ExactQuest q = new ExactQuest("first quest", false);
        Landmark firstLandmark = new Landmark("Martini Toren", 1);
        firstLandmark.setInformation(
                "The martini toren is arguably Groningen's most well know landmark,\n"
                        + "it has a height of 97 m and is Groningens heighest church \n"
        );
        firstLandmark.setPoints(50);
        firstLandmark.setLocationName("Martinikerkhof 3");
        q.addLandmark(firstLandmark);
    }

}
