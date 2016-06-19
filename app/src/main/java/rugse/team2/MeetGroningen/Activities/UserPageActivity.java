package rugse.team2.MeetGroningen.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import rugse.team2.MeetGroningen.DatabaseStuff.DatabaseHelper;
import rugse.team2.MeetGroningen.Objects.Quest;
import rugse.team2.MeetGroningen.Objects.User;

import java.util.ArrayList;

/**
 * This class represents the activity (Android window) for a user's profile page.
 * It displays the current user's earned points and a list of their completed quests.
 */
public class UserPageActivity extends AppCompatActivity {
    /** The text lay-out element to be filled with the amount of points. */
    private TextView tv;
    /** The amount of points earned so far by the current user. */
    private int points;
    /** The list lay-out element to be filled with the names of all quests completed so far by the current user. */
    private ListView listview;

    /**
     * Initialises the activity as described above, creating all required views.
     *
     * @param savedInstanceState If the activity is being re-initialised after previously being shut down, then this Bundle
     *                           contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_user_page);

        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        User user = helper.getUser(db);
        points = user.getPoints();
        //display the amount of points
        tv = (TextView) findViewById(rugse.team2.MeetGroningen.R.id.textView8);
        tv.setText(Integer.toString(points));

        //display the list of solved quests
        listview = (ListView) findViewById(rugse.team2.MeetGroningen.R.id.listView5);
        ArrayList<Quest> list = user.getSolvedQuests();
        Quest[] quests = list.toArray(new Quest[list.size()]);
        ArrayAdapter<Quest> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quests);
        listview.setAdapter(adapter);

    }
}
