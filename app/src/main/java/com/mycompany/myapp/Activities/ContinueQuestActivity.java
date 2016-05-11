package com.mycompany.myapp.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.R;
import com.mycompany.myapp.Objects.User;

import java.util.ArrayList;

/**
 * This class represents the activity (Android window) for resuming a quest.
 * It gets all started quests from the database and displays them in a list.
 *
 * Created by Ruben on 19-03-2016.
 */
public class ContinueQuestActivity extends AppCompatActivity {
    private Quest chosenQuest; //the clicked quest, to be passed on to the next activity
    private User user; //the particular user the displayed set of started quests belongs to
    private DatabaseHelper helper; //the helper instance for exchanging information with the database

    /* Initialises the activity as described above, and binds clicking a quest to starting a new OnQuestActivity. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuequest);


        helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        user = helper.getUser(db);

        db.close();
        helper.close();

        ListView listView = (ListView) findViewById(R.id.listView1);

        //redirect to quest explanation page, passing the chosen quest, also updating the active quest to be the selected quest
        try {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    helper = new DatabaseHelper(getBaseContext());
                    user.setActiveQuest((Quest) parent.getAdapter().getItem(position));
                    helper.updateInDatabase(helper, user);
                    helper.close();

                    chosenQuest = (Quest) parent.getAdapter().getItem(position);
                    Log.d("TEST", "clicked active quest list, passed quest: " + chosenQuest.toString());
                    Intent i = new Intent(getBaseContext(), OnQuestActivity.class);
                    i.putExtra("PassedQuest", chosenQuest);
                    startActivity(i);
                }
            });
        }catch(NullPointerException e){
            Log.e("ListItem Error", "A list item is null: " + e);
        }


        //get all current quests and put them in a list view
        ArrayList<Quest> currentQuests = user.getCurrentQuests();

        ArrayAdapter<Quest> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, currentQuests);
        listView.setAdapter(adapter2);


    }
}
