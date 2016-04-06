package com.mycompany.myapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class QuestFinishedActivity extends AppCompatActivity {

    private Quest finishedQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_finished);
        TextView questname = (TextView)findViewById(R.id.textView2);
        questname.setText(finishedQuest.name);
        questname.setVisibility(View.VISIBLE);

        finishedQuest = (Quest) getIntent().getSerializableExtra("finishedQuest");
    }

}
