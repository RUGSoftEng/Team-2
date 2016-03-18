package com.mycompany.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Ruben on 17/03/2016.
 */
public class QuestExplanationActivity extends AppCompatActivity{

    Quest passedQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questexplanation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        passedQuest = (Quest) getIntent().getSerializableExtra("PassedQuest");

        ListView listView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<Landmark> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passedQuest.getLandmarks());
        listView.setAdapter(adapter);

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


    }
}
