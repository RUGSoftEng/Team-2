package com.mycompany.myapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.R;

public class QuestFinishedActivity extends AppCompatActivity {

    private Quest finishedQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_finished);
        finishedQuest = (Quest) getIntent().getSerializableExtra("finishedQuest");
        TextView questname = (TextView)findViewById(R.id.textView2);
        if (finishedQuest.name != null) {
            questname.setText(finishedQuest.name);
            questname.setVisibility(View.VISIBLE);
        }
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
