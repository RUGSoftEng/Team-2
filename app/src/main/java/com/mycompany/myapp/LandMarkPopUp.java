package com.mycompany.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Lutz on 21-3-2016.
 */
public class LandMarkPopUp extends Activity {

    private Landmark passedLandmark;
    private TextView textInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        passedLandmark = (Landmark) getIntent().getSerializableExtra("PassedLandmark");
//        for test
//        Landmark academieGebouw = new Landmark("Academiegebouw", 99);
//        academieGebouw.setLocation(53.219203, 6.563126);
//        academieGebouw.setInformation("The Academiegebouw is the main building of the university; its richly decorated exterior was completed in 1909.");


        textInfo = (TextView) findViewById(R.id.textView4);
        textInfo.setText(passedLandmark.getInformation());





    }
}
