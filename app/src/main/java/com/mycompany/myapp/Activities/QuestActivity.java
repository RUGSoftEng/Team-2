package com.mycompany.myapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycompany.myapp.R;

/**
 * Class description goes here.
 */
public class QuestActivity extends AppCompatActivity {
    /* Method description goes here. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /* Method description goes here. */
    public void CheckLocation(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        boolean checked = ((CheckBox) view).isChecked();
        tv.setText("Did you know the Martinitower is 97 meters?");
            if (checked) {
                linearLayout.addView(tv);
            }
    }

}
