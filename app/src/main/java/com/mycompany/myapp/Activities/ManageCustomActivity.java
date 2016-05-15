package com.mycompany.myapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.Objects.User;

import java.util.ArrayList;

/**
 * Created by Ruben on 15/05/2016.
 */
public class ManageCustomActivity extends Activity {


    Button sendQuest;
    Button sendLandmark;

    Button deleteQuest;  //Could also be long click
    Button deleteLandmark; //Could also be long click

    private ArrayList<Quest> customQuestList;
    private ArrayList<Landmark> customLandmarkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DatabaseHelper helper = new DatabaseHelper(this);
        User user = helper.getUser(helper.getReadableDatabase());
        for(Quest quest : user.getCurrentQuests()){
            if(quest.isUserGenerated()){
                customQuestList.add(quest);
            }
        }

        customLandmarkList = helper.getAllLandmarks(helper.getReadableDatabase());
    }
}
