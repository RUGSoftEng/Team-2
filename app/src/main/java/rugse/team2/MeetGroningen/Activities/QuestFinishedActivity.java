package rugse.team2.MeetGroningen.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rugse.team2.MeetGroningen.Objects.Quest;

/**
 * This class represents the activity (Android window) for displaying
 * a congratulatory message regarding having finished a certain quest.
 */
public class QuestFinishedActivity extends AppCompatActivity {
    /** the finished quest, passed by the previous activity */
    private Quest finishedQuest;

    /** Initialises the activity as described above, and binds clicking a 'go back to main' button to starting a new MainActivity. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_quest_finished);
        finishedQuest = (Quest) getIntent().getSerializableExtra("finishedQuest");
        TextView questname = (TextView)findViewById(rugse.team2.MeetGroningen.R.id.congratsText_questFinished);
        if (finishedQuest.getName() != null) {
            questname.setText(finishedQuest.getName());
            questname.setVisibility(View.VISIBLE);
        }

        Button pickQuest = (Button) findViewById(rugse.team2.MeetGroningen.R.id.goMainButton_questFinished);
        pickQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    /** Finishes this activity and returns to the main screen when the 'back' button is pressed. */
    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
        return;
    }
}
