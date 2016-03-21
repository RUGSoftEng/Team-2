package com.mycompany.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Ruben on 17/03/2016.
 */
public class QuestExplanationActivity extends AppCompatActivity{

    private Quest passedQuest;

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
                //TODO on click this should show the marker of selected landmark?
            }
        });

        Button addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* TODO user not yet implemented in database
                DatabaseHelper helper = new DatabaseHelper(this);
                SQLiteDatabase db = helper.getReadableDatabase();
                User user = helper.getUser(db);
                user.add(this.passedQuest);
                helper.updateUser(db, user);
                */

            }
        });

    }
}
