package com.mycompany.myapp.Objects;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class User implements Serializable {

    private int points;
    private String userID, name;

    //private ArrayList<Achievement> achievements; TODO: not yet implemented
    private ArrayList<Quest> currentQuests = new ArrayList<>();
    private ArrayList<Quest> solvedQuests = new ArrayList<>();
    private Quest activeQuest;

    public User(String userID){
        this.userID = userID;
    }

    public void addQuest(Quest q) {
        this.currentQuests.add(q);
        Log.d("TEST", "Added a quest to currentQuest: " + q.toString());
    }

    public int getPoints(){
        return this.points;
    }

    public void addPoints(int i){
        this.points = this.points + i;
    }

    public void finishQuest(Quest q){ //add a quest to the solved and remove it from current quest list
        this.currentQuests.remove(q);
        this.solvedQuests.add(q);
        if(q == activeQuest){
            this.activeQuest = null;
        }
    }

    public String getID(){
        return this.userID;
    }

    public String getName(){
        return this.name;
    }

    public Quest getActiveQuest(){
        return activeQuest;
    }

    public ArrayList<Quest> getSolvedQuests() { return solvedQuests; }

    public ArrayList<Quest> getCurrentQuests() {
        return currentQuests;
    }

    public void setActiveQuest(Quest q) {
        this.activeQuest = q;
    }

}
