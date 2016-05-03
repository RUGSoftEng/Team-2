package com.mycompany.myapp.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.Objects.User;
import com.mycompany.myapp.R;

import java.util.ArrayList;

public class UserPageActivity extends AppCompatActivity {
    private TextView tv;
    private int points;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        User user = helper.getUser(db);
        points = user.getPoints();
        // display the amount of points
        tv = (TextView) findViewById(R.id.textView8);
        tv.setText(Integer.toString(points));

        // display the list of solved quests
        listview = (ListView) findViewById(R.id.listView5);
        ArrayList<Quest> list = user.getSolvedQuests();
        Quest[] quests = list.toArray(new Quest[list.size()]);
        ArrayAdapter<Quest> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quests);
        listview.setAdapter(adapter);

    }
}
