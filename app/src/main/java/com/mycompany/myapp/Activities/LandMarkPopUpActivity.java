package com.mycompany.myapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.R;

/**
 * Created by Lutz on 21-3-2016.
 */
public class LandMarkPopUpActivity extends Activity {

    private Landmark passedLandmark;
    private TextView textInfo;
    private Button nextLandmarkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        passedLandmark = (Landmark) getIntent().getSerializableExtra("passedLandmark");

        textInfo = (TextView) findViewById(R.id.textView4);
        textInfo.setMovementMethod(new ScrollingMovementMethod());
        textInfo.setText(passedLandmark.getInformation());

        nextLandmarkButton = (Button) findViewById(R.id.nextLandmarkButton);
        nextLandmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
