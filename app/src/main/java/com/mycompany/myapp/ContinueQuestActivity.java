package com.mycompany.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by Ruben on 19/03/2016.
 */
public class ContinueQuestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuequest);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContinueQuestActivity.this, LandMarkPopUp.class));
            }
        });


    }

    /*
    //redirect to quest content page, passing the chosen quest
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(getBaseContext(), QuestActivity.class);
            i.putExtra("PassedQuest", passedQuest);
            startActivity(i);
            //Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
            //        Toast.LENGTH_SHORT).show();
        }
    });
    */
}
