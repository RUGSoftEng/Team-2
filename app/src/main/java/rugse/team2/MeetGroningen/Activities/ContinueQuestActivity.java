package rugse.team2.MeetGroningen.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rugse.team2.MeetGroningen.DatabaseStuff.DatabaseHelper;
import rugse.team2.MeetGroningen.Objects.Quest;
import rugse.team2.MeetGroningen.Objects.User;

import java.util.ArrayList;

/**
 * This class represents the activity (Android window) for resuming a quest.
 * It gets all started quests from the database and displays them in a list.
 *
 * Created by Ruben on 19-03-2016.
 */
public class ContinueQuestActivity extends AppCompatActivity {
    /** the clicked quest, to be passed on to the next activity */
    private Quest chosenQuest;
    /** the particular user the displayed set of started quests belongs to */
    private User user;
    /** the helper instance for exchanging information with the database */
    private DatabaseHelper helper;

    /** Initialises the activity as described above, and binds clicking a quest to starting a new OnQuestActivity. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_continuequest);

        helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        user = helper.getUser(db);
        db.close();
        helper.close();

        final ListView listView = (ListView) findViewById(rugse.team2.MeetGroningen.R.id.listView1);

        //redirect to quest explanation page, passing the chosen quest, also updating the active quest to be the selected quest
        if(listView != null) {
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
        }

        //get all current quests and put them in a list view
        ArrayList<Quest> currentQuests = user.getCurrentQuests();

        final ArrayAdapter<Quest> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, currentQuests);
        if (listView != null) {
            listView.setAdapter(adapter2);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContinueQuestActivity.this);

                    builder.setNeutralButton(getResources().getString(rugse.team2.MeetGroningen.R.string.deleteButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            chosenQuest = (Quest) parent.getAdapter().getItem(position);
                            helper = new DatabaseHelper(getBaseContext());
                            user.getCurrentQuests().remove(chosenQuest);
                            helper.updateInDatabase(helper, user);
                            helper.close();
                            adapter2.notifyDataSetChanged();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    return true;
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
        return;
    }
}
