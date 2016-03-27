package com.mycompany.myapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ruben on 19/03/2016.
 */
public class ContinueQuestActivity extends AppCompatActivity {
    private Quest chosenQuest;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuequest);


        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        user = helper.getUser(db);

        db.close();
        helper.close();


        //get active quest and put in listview, now for only 1 active quest at the time
        Quest[] activeQuests = new Quest[1];
        if(user.getActiveQuest() != null){
            activeQuests[0] = user.getActiveQuest();
        }

        ListView listView = (ListView) findViewById(R.id.activeQuest);
        ArrayAdapter<Quest> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activeQuests);
        listView.setAdapter(adapter);


        //redirect to quest explanation page, passing the chosen quest
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                chosenQuest = (Quest) parent.getAdapter().getItem(position);
                Log.d("TEST", "clicked active quest list");
                Intent i = new Intent(getBaseContext(), OnQuestActivity.class);
                i.putExtra("PassedQuest", chosenQuest);
                startActivity(i);
            }
        });


        //get all current quests and put them in listview
        ArrayList<Quest> currentQuests = user.getCurrentQuests();
        if(!user.getCurrentQuests().isEmpty()) {
            currentQuests.remove(user.getActiveQuest());
        }

        Quest[] userQuests = currentQuests.toArray(new Quest[currentQuests.size()]);

        //TODO: A element is null in the list of currentquests and so there will be a null pointer here, and why an error when listview2 has adapter2?(listview1 does work)

        ListView listView2 = (ListView) findViewById(R.id.userQuests);
        ArrayAdapter<Quest> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userQuests);
        Log.d("TEST", "listview2: " + listView2 + " ,adapter2: " + adapter2);
        listView.setAdapter(adapter2);


        //redirect to quest explanation page, passing the chosen quest
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //new active quest is selected so replace old active with new
                chosenQuest = (Quest) parent.getAdapter().getItem(position);
                user.setActiveQuest(chosenQuest);

                DatabaseHelper helper = new DatabaseHelper(getBaseContext());
                helper.updateInDatabase(helper, user);

                helper.close();

                Log.d("TEST", "clicked current quest list");
                Intent i = new Intent(getBaseContext(), OnQuestActivity.class);
                i.putExtra("PassedQuest", chosenQuest);
                startActivity(i);
            }
        });

    }
}
