package rugse.team2.MeetGroningen.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;


/**
 * This class represents the pop-up activity for choosing a specific kind of new quest.
 * It includes two buttons, one for picking an existing quest, and one for creating one's own.
 *
 * Created by Lutz on 21-3-2016.
 */
public class NewQuestPopupActivity extends Activity {


    /* Initialises the activity as described above, and binds 'existing' to starting
     * a new ChooseQuestActivity and 'own' to starting a new MakeQuestActivity. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rugse.team2.MeetGroningen.R.layout.activity_new_quest_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.3));

        Button ownQuest = (Button) findViewById(rugse.team2.MeetGroningen.R.id.ownQuestButton); //the button for creating one's own quest
        Button existingQuest = (Button) findViewById(rugse.team2.MeetGroningen.R.id.existingQuestButton); //the button for picking an existing quest
        Button makeLandmarkButton = (Button) findViewById(rugse.team2.MeetGroningen.R.id.makeLandmarkButton);

        ownQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MakeQuestActivity.class);
                startActivity(i);
            }
        });

        existingQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ChooseQuestActivity.class);
                startActivity(i);
            }
        });

        makeLandmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MakeLandmarkActivity.class);
                startActivity(i);
            }
        });


    }
}
