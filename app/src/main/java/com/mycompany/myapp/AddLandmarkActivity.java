package com.mycompany.myapp;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Ruben on 28/02/2016.
 */

//TODO: this class should be linked to the button for adding a landmark to your quest
public class AddLandmarkActivity extends Activity {

    Button ADD, SELECT;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_landmark);
        ADD = (Button) findViewById(R.id.addButton);

        ListView listView = (ListView) findViewById(R.id.listView);

        //TODO: now hardcoded, has to be done for every Landmark in the database, binded to arrayAdapter to print each value of the landmark
        Landmark[] landmarks = {
                new Landmark("Martini Toren", 1),
                new Landmark("A Kerk", 2),
        };


        ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this,
                android.R.layout.simple_list_item_1, landmarks);

        listView.setAdapter(adapter);



        //SELECT = (Button) findViewById(R.id."copy appropriate id of button here(xml)"); TODO: add button from layout here
        /*SELECT.setOnClickListener(new OnClickListener(){ TODO: add here button listiner and what to do (select in this case)

            @Override
            public void onClick(View v){

            }

        });
        */

        ADD.setOnClickListener(new View.OnClickListener(){ //TODO: add here button listiner and what to do (add selected Landmark in this case)

            @Override
            public void onClick(View v){


            }

        });
    }
}
