package com.mycompany.myapp.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.R;

import java.util.ArrayList;

/**
 * Class description goes here.
 */
public class ChooseQuestActivity extends AppCompatActivity {
    private ListView listView; //field description goes here
    private Quest chosenQuest; //field description goes here

    /* Method description goes here. */
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

                Intent i = new Intent(getBaseContext(), QuestPreviewActivity.class);
                i.putExtra("PassedQuest", chosenQuest);
                startActivity(i);
            }
        });

    }

}
