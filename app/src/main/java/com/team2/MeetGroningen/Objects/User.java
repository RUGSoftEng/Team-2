package com.team2.MeetGroningen.Objects;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents an application user, which is a profile belonging to a real person,
 * which includes their name, amount of obtained points, started quests, and completed quests.
 *
 * Created by Ruben on 23-02-2016.
 */
public class User implements Serializable {

    private int points = 0; //the amount of points the user has obtained up to now
    private String userID; //the user's unique ID
    private String name; //the user's name

    //private ArrayList<Achievement> achievements; TODO: not yet implemented
    private ArrayList<Quest> currentQuests = new ArrayList<>(); //the list of quests started but not yet completed by this user
    private ArrayList<Quest> solvedQuests = new ArrayList<>(); //the list of quests already completed by this user
    private Quest activeQuest; //the currently active quest, which is the quest the user is on currently

    /* Constructor which stores a given user ID. */
    public User(String userID){
        this.userID = userID;
    }

    /* Adds the given quest to the user's list of started quests. */
    public void addQuest(Quest q) {
        this.currentQuests.add(q);
        Log.d("TEST", "Added a quest to currentQuest: " + q.toString());
    }

    /* Getter method for the amount of points obtained. */
    public int getPoints(){
        return this.points;
    }

    /* Adds the given amount of points to the user's amount of points. */
    public void addPoints(int i){
        this.points = this.points + i;
    }

    /* Adds the given quest to the user's list of completed quests after removing it from their list of started quests. */
    public void finishQuest(Quest q){
        this.currentQuests.remove(q);
        this.solvedQuests.add(q);
        if(q == activeQuest){
            this.activeQuest = null;
        }
    }

    /* Getter method for the user's ID. */
    public String getID(){
        return this.userID;
    }

    /* Getter method for the user's name. */
    public String getName(){
        return this.name;
    }

    /* Getter method for the currently active quest. */
    public Quest getActiveQuest(){
        return activeQuest;
    }

    /* Getter method for the list of already completed quests. */
    public ArrayList<Quest> getSolvedQuests() { return solvedQuests; }

    /* Getter method for the list of started but not yet completed quests. */
    public ArrayList<Quest> getCurrentQuests() {
        return currentQuests;
    }

    /* Setter method for the currently active quest. */
    public void setActiveQuest(Quest q) {
        this.activeQuest = q;
    }

}
