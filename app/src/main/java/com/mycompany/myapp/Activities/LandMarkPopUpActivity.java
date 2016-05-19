package com.mycompany.myapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quiz;
import com.mycompany.myapp.R;

/**
 * This class represents the pop-up activity for displaying information about a reached landmark.
 * It includes a 'next' button for continuing the quest by starting the search for the next landmark,
 * as well as a 'quiz' button for answering multiple choice questions about the landmark just found.
 *
 * Created by Lutz on 21-3-2016.
 */
public class LandMarkPopUpActivity extends Activity {

    private Landmark passedLandmark; //the reached landmark, passed by the previous activity
    private Button quizButton; //the button for starting a quiz about the current landmark
    public Quiz quiz; //the quiz corresponding to the current landmark
    private String[] items; //the available answers for the multiple choice questions

    /* Initialises the activity as described above, and binds 'next' to closing the pop-up
     * and 'quiz' to changing it into a multiple choice question with buttons for answers. */
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

        TextView textInfo = (TextView) findViewById(R.id.textView4);
        textInfo.setMovementMethod(new ScrollingMovementMethod());
        textInfo.setText(passedLandmark.getInformation());

        Button nextLandmarkButton = (Button) findViewById(R.id.nextLandmarkButton);
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
                //quiz = passedLandmark.getQuiz();
                //items = quiz.getPossibleAnswers();
                items = new String[]{"answer A", "answer B", "answer C"};
                AlertDialog.Builder builder = new AlertDialog.Builder(LandMarkPopUpActivity.this);
                //builder.setTitle(quiz.getQuestion());
                builder.setTitle("Examplequestion?");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //do something with the selection
                        quizButton.setText(items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
