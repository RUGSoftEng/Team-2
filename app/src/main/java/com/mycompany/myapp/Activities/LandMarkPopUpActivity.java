package com.mycompany.myapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Context;
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
 * Created by Lutz on 21-3-2016.
 */
public class LandMarkPopUpActivity extends Activity {

    private Landmark passedLandmark;
    private TextView textInfo;
    private Button nextLandmarkButton;
    private Button quizButton;
    private Quiz quiz;
    private String[] items;

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

        quizButton = (Button) findViewById(R.id.quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz = passedLandmark.getQuiz();
                // items = quiz.getPossibleAnswers();
                items = new String[]{"answer A", "answer B", "answer C"};
                AlertDialog.Builder builder = new AlertDialog.Builder(LandMarkPopUpActivity.this);
                //builder.setTitle(quiz.getQuestion());
                builder.setTitle("Examplequestion?");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        quizButton.setText(items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
