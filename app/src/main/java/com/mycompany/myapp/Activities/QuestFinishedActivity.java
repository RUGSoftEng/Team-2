package com.mycompany.myapp.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.Objects.User;
import com.mycompany.myapp.R;

public class QuestFinishedActivity extends AppCompatActivity {

    private Quest finishedQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_finished);
        finishedQuest = (Quest) getIntent().getSerializableExtra("finishedQuest");
        TextView questname = (TextView)findViewById(R.id.textView2);
        if (finishedQuest.getName() != null) {
            questname.setText(finishedQuest.getName());
            questname.setVisibility(View.VISIBLE);
        }

//        DatabaseHelper helper = new DatabaseHelper(getBaseContext());
//        SQLiteDatabase db = helper.getReadableDatabase();
//        User user = helper.getUser(db);
//
//        user.finishQuest(finishedQuest);
//        helper.updateInDatabase(helper, user);

        // TODO: button that redirects user to main page
        Button pickQuest = (Button) findViewById(R.id.button_main);
        pickQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

}
