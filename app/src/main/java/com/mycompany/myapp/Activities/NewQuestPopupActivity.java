package com.mycompany.myapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quiz;
import com.mycompany.myapp.R;

import java.util.ArrayList;

/**
 * This class represents the pop-up activity for choosing a specific kind of new quest.
 * It includes two buttons, one for picking an existing quest, and one for creating one's own.
 *
 * Created by Lutz on 21-3-2016.
 */
public class NewQuestPopupActivity extends Activity {

    private Button ownQuest; //the button for creating one's own quest
    private Button existingQuest; //the button for picking an existing quest

    /* Initialises the activity as described above, and binds 'existing' to starting
     * a new ChooseQuestActivity and 'own' to starting a new MakeQuestActivity. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quest_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.3));

        ownQuest = (Button) findViewById(R.id.ownQuestButton);
        existingQuest = (Button) findViewById(R.id.existingQuestButton);

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


    }
}
