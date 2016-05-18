package com.mycompany.myapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mycompany.myapp.DatabaseStuff.DatabaseHelper;
import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.Objects.User;
import com.mycompany.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruben on 15/05/2016.
 */
public class ManageCustomActivity extends Activity {


    private Button sendQuestButton;
    private Button sendLandmarkButton;

    private Button deleteQuestButton;  //Could also be long click
    private Button deleteLandmarkButton; //Could also be long click

    private Landmark selectedLandmark;
    private Quest selectedQuest;

    private TextView selectedQuestText;
    private TextView selectedLandmarkText;

    private ArrayList<Quest> customQuestList;
    private ArrayList<Landmark> customLandmarkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managecustom);

        customQuestList = new ArrayList<>();
        customLandmarkList = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(this);
        User user = helper.getUser(helper.getReadableDatabase());
        for(Quest quest : user.getCurrentQuests()){
            if(quest.isUserGenerated()){
                customQuestList.add(quest);
            }
        }

        selectedQuestText = (TextView) findViewById(R.id.customQuestText);
        selectedLandmarkText = (TextView) findViewById(R.id.customLandmarkText);

        for(Landmark l : helper.getAllLandmarks(helper.getReadableDatabase())){
            if(l.isUserGenerated()) {
                customLandmarkList.add(l);
            }
        }

        ListView customLandmarksView = (ListView) findViewById(R.id.customLandmarkListView);
        final ArrayAdapter adapterL = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customLandmarkList);
        customLandmarksView.setAdapter(adapterL);

        ListView customQuestView = (ListView) findViewById(R.id.customQuestListView);
        final ArrayAdapter adapterQ = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customQuestList);
        customQuestView.setAdapter(adapterQ);

        deleteQuestButton = (Button) findViewById(R.id.deleteQuestButton);
        deleteLandmarkButton = (Button) findViewById(R.id.deleteLandmarkButton);
        sendLandmarkButton = (Button) findViewById(R.id.sendLandmarkButton);
        sendQuestButton = (Button) findViewById(R.id.sendQuestButton);

        //TODO: these have to be visible again when implemented
        sendQuestButton.setVisibility(View.INVISIBLE);
        sendLandmarkButton.setVisibility(View.INVISIBLE);


        customQuestView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedQuest = (Quest) parent.getAdapter().getItem(position);
                selectedQuestText.setText(selectedQuest.getName());
            }
        });

        customLandmarksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedLandmark = (Landmark) parent.getAdapter().getItem(position);
                selectedLandmarkText.setText(selectedLandmark.getName());
            }
        });


        deleteLandmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLandmark != null) {
                    DatabaseHelper helper = new DatabaseHelper(getBaseContext());
                    helper.deleteLandmark(helper, selectedLandmark.getID());
                    customLandmarkList.remove(selectedLandmark);
                    selectedLandmark = null;
                    helper.close();
                }
                adapterL.notifyDataSetChanged();
            }
        });

        deleteQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedQuest != null) {
                    DatabaseHelper helper = new DatabaseHelper(getBaseContext());
                    User u = helper.getUser(helper.getReadableDatabase());
                    //TODO: could go more efficient?, now quick fix
                    int i = 0;
                    for(Quest q : u.getCurrentQuests()){
                        if(q.getID().equals(selectedQuest.getID())){
                            u.getCurrentQuests().remove(i);
                        }
                        i++;
                    }

                    helper.updateInDatabase(helper, u);
                    helper.close();
                    customQuestList.remove(selectedQuest);
                    selectedQuest = null;
                }
                adapterQ.notifyDataSetChanged();
            }
        });
    }
}
