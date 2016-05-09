package com.mycompany.myapp.Objects;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class description goes here.
 *
 * Created by Ruben on 23/02/2016.
 */
public class User implements Serializable {

    private int points = 0; //field description goes here
    private String userID; //field description goes here
    private String name; //field description goes here

    //private ArrayList<Achievement> achievements; TODO: not yet implemented
    private ArrayList<Quest> currentQuests = new ArrayList<>(); //field description goes here
    private ArrayList<Quest> solvedQuests = new ArrayList<>(); //field description goes here
    private Quest activeQuest; //field description goes here

    /* Method description goes here. */
    public User(String userID){
        this.userID = userID;
    }

    /* Method description goes here. */
    public void addQuest(Quest q) {
        this.currentQuests.add(q);
        Log.d("TEST", "Added a quest to currentQuest: " + q.toString());
    }

    /* Method description goes here. */
    public int getPoints(){
        return this.points;
    }

    /* Method description goes here. */
    public void addPoints(int i){
        this.points = this.points + i;
    }

    /* Method description goes here. */
    public void finishQuest(Quest q){ //add a quest to the solved and remove it from current quest list
        this.currentQuests.remove(q);
        this.solvedQuests.add(q);
        if(q == activeQuest){
            this.activeQuest = null;
        }
    }

    /* Method description goes here. */
    public String getID(){
        return this.userID;
    }

    /* Method description goes here. */
    public String getName(){
        return this.name;
    }

    /* Method description goes here. */
    public Quest getActiveQuest(){
        return activeQuest;
    }

    /* Method description goes here. */
    public ArrayList<Quest> getSolvedQuests() { return solvedQuests; }

    /* Method description goes here. */
    public ArrayList<Quest> getCurrentQuests() {
        return currentQuests;
    }

    /* Method description goes here. */
    public void setActiveQuest(Quest q) {
        this.activeQuest = q;
    }

}
