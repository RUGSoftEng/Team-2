package com.mycompany.myapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    ArrayList<Integer> imgs = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Buttons
        Button pickQuest = (Button) findViewById(R.id.button_main);
        pickQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), NextActivity.class);
                startActivity(i);
            }
        });



        Button makeQuest = (Button) findViewById(R.id.createQuest);
        makeQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddLandmarkActivity.class);
                startActivity(i);
            }
        });



        //make image switcher to switch background
        //NOTE: For some reason martini.jpg doesn't work so their might be pictures not working
        imgs.add(R.drawable.martini2);
        imgs.add(R.drawable.martini3);
        imgs.add(R.drawable.martini4);
        imgs.add(R.drawable.koala);


        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                return myView;
            }
        });
                //should always have a element otherwise null pointer exception TODO: catch any exceptions/errors
                imageSwitcher.postDelayed(new Runnable() {
                    int i = 0;

                    public void run() {
                        imageSwitcher.setImageResource(imgs.get(i));
                        i++;
                        if(i == imgs.size()) i = 0;
                        imageSwitcher.postDelayed(this, 10000);
                    }
                }, 10000);

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        imageSwitcher.setInAnimation(in);
        //imageSwitcher.setOutAnimation(out);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
