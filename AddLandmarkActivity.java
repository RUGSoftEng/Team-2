package com.example.ruben.softwareengeneering20;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

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
        //setContentView(R.layout."copy appropiate layout here(xml)"); TODO: So add layout here
        //ADD = (Button) findViewById(R.id."copy appropriate id of button here(xml)"); TODO: add button from layout here
        //SELECT = (Button) findViewById(R.id."copy appropriate id of button here(xml)"); TODO: add button from layout here
        /*SELECT.setOnClickListener(new OnClickListener(){ TODO: add here button listiner and what to do (select in this case)

            @Override
            public void onClick(View v){

            }

        });

        ADD.setOnClickListener(new OnClickListener(){ TODO: add here button listiner and what to do (add selected Landmark in this case)

            @Override
            public void onClick(View v){

            }

        });
        */
    }
}
