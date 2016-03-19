package com.mycompany.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ChooseQuestActivity extends AppCompatActivity {
    private ListView listView;
    private Quest chosenQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosequest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<Quest> a = helper.getAllQuests(db);
        Quest[] quests = a.toArray(new Quest[a.size()]);

        db.close();
        helper.close();

        listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<Quest> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quests);
        listView.setAdapter(adapter);


        //redirect to quest explanation page, passing the chosen quest
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                chosenQuest = (Quest) parent.getAdapter().getItem(position) ;

                Intent i = new Intent(getBaseContext(), QuestExplanationActivity.class);
                i.putExtra("PassedQuest", chosenQuest);
                startActivity(i);
            }
        });

    }

}
