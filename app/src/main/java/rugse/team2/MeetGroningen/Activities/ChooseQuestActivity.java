package rugse.team2.MeetGroningen.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rugse.team2.MeetGroningen.DatabaseStuff.DatabaseHelper;
import rugse.team2.MeetGroningen.Objects.Quest;

import java.util.ArrayList;

/**
 * This class represents the activity (Android window) for choosing a quest.
 * It gets all available quests from the database and displays them in a list.
 */
public class ChooseQuestActivity extends AppCompatActivity {
    /** The clicked quest, to be passed on to the next activity. */
    private Quest chosenQuest;

    /**
     * Initialises the activity as described above, and binds clicking a quest to starting a new QuestPreviewActivity.
     *
     * @param savedInstanceState If the activity is being re-initialised after previously being shut down, then this Bundle
     *                           contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_choosequest);
        Toolbar toolbar = (Toolbar) findViewById(rugse.team2.MeetGroningen.R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<Quest> a = helper.getAllQuests(db);
        Quest[] quests = a.toArray(new Quest[a.size()]);

        db.close();
        helper.close();

        ListView listView = (ListView) findViewById(rugse.team2.MeetGroningen.R.id.list_view);
        ArrayAdapter<Quest> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quests);
        if (listView != null) {
            listView.setAdapter(adapter);


            //redirect to quest explanation page, passing the chosen quest
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    chosenQuest = (Quest) parent.getAdapter().getItem(position);

                    Intent i = new Intent(getBaseContext(), QuestPreviewActivity.class);
                    i.putExtra("PassedQuest", chosenQuest);
                    startActivity(i);
                }
            });
        }

    }

}
