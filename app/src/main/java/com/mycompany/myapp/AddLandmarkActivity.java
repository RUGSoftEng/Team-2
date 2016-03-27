package com.mycompany.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by Ruben on 28/02/2016.
 */

//TODO: this class should be linked to the button for adding a landmark to your quest
public class AddLandmarkActivity extends AppCompatActivity {

    Button ADD, SELECT;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_landmark);
        ADD = (Button) findViewById(R.id.addButton);
        ListView listView = (ListView) findViewById(R.id.listView);


        //take all landmark objects from the database and put them into a listView
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<Landmark> a = helper.getAllLandmarks(db);
        Landmark[] landmarks = a.toArray(new Landmark[a.size()]);


        ArrayAdapter<Landmark> adapter = new ArrayAdapter<Landmark>(this,
                android.R.layout.simple_list_item_1, landmarks);

        listView.setAdapter(adapter);

        db.close();
        helper.close();

        //SELECT = (Button) findViewById(R.id."copy appropriate id of button here(xml)"); TODO: add button from layout here
        /*SELECT.setOnClickListener(new OnClickListener(){ TODO: add here button listiner and what to do (select in this case)

            @Override
            public void onClick(View v){

            }

        });
        */

        ADD.setOnClickListener(new View.OnClickListener() { //TODO: add here button listiner and what to do (add selected Landmark in this case)

            @Override
            public void onClick(View v) {


            }
        });
    }
}

