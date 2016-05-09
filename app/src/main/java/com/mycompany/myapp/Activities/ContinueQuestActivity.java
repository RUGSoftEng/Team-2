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
 * Class description goes here.
 *
 * Created by Ruben on 19/03/2016.
 */
public class ContinueQuestActivity extends AppCompatActivity {
    private Quest chosenQuest; //field description goes here
    private User user; //field description goes here
    private DatabaseHelper helper; //field description goes here

    /* Method description goes here. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuequest);


        helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        user = helper.getUser(db);

        db.close();
        helper.close();

        ListView listView = (ListView) findViewById(R.id.listView1);

        //redirect to quest explanation page, passing the chosen quest, also update the active quest to be the selected quest
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


        //get all current quests and put them in listview
        ArrayList<Quest> currentQuests = user.getCurrentQuests();

        ArrayAdapter<Quest> adapter2 = new ArrayAdapter<Quest>(this, android.R.layout.simple_expandable_list_item_1, currentQuests);
        listView.setAdapter(adapter2);


    }
}
