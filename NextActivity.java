package com.example.ruben.softwareengeneering20;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    }

}
