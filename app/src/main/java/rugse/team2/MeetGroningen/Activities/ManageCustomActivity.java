package rugse.team2.MeetGroningen.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rugse.team2.MeetGroningen.DatabaseStuff.DatabaseHelper;
import rugse.team2.MeetGroningen.Objects.Landmark;
import rugse.team2.MeetGroningen.Objects.Quest;
import rugse.team2.MeetGroningen.Objects.User;
import rugse.team2.MeetGroningen.R;

import java.util.ArrayList;

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
        customQuestList = user.getCurrentQuests();

        selectedQuestText = (TextView) findViewById(R.id.questText);
        selectedLandmarkText = (TextView) findViewById(R.id.customLandmarkText);

        for (Landmark l : helper.getAllLandmarks(helper.getReadableDatabase())) {
            if (l.isUserGenerated()) {
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
                    selectedLandmarkText.setText(R.string.customLandmarkText);
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

                    u.getCurrentQuests().remove(selectedQuest);

                    helper.updateInDatabase(helper, u);
                    helper.close();
                    customQuestList.remove(selectedQuest);
                    selectedQuest = null;
                    selectedQuestText.setText(R.string.questText);
                }
                adapterQ.notifyDataSetChanged();
            }
        });

        sendQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedQuest != null) {
                    if (selectedQuest.isSend()) {
                        Toast.makeText(getApplicationContext(), R.string.AlreadyOnServerText, Toast.LENGTH_SHORT).show();
                    } else {
                        selectedQuest.setIsSend(true);
                        selectedQuest.putOnServer(); //TODO: change the putOnServer for custom objects too something that puts them in special seperate database
                        selectedQuestText.setText(R.string.questText);
                    }
                }
                adapterQ.notifyDataSetChanged();
            }
        });
        sendLandmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLandmark != null) {
                    if (selectedLandmark.isSend()) {
                        Toast.makeText(getApplicationContext(), R.string.AlreadyOnServerText, Toast.LENGTH_SHORT).show();
                    } else {
                        selectedLandmark.setIsSend(true);
                        selectedLandmark.putOnServer(); //TODO: change the putOnServer for custom objects too something that puts them in special seperate database
                        selectedLandmarkText.setText(R.string.questText);
                    }
                }
                adapterL.notifyDataSetChanged();
            }
        });
    }
}
