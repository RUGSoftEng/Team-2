package com.mycompany.myapp;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class User implements Serializable {

    private int userID, points;
    private String name;

    //private ArrayList<Achievement> achievements; TODO: not yet implemented
    private ArrayList<Quest> quests = new ArrayList<Quest>(), solvedquests = new ArrayList<Quest>();
    private Quest activeQuest;

    public User(int userID){
        this.userID = userID;
    }

    public void addQuest(Quest q) { this.quests.add(q);    }

    public int getPoints(){
        return this.points;
    }

    public void addPoints(int i){
        this.points = this.points + i;
    }

    public void finishQuest(Quest q){ //add a quest to the solved and remove it from current quest list
        this.quests.remove(q);
        this.solvedquests.add(q);
    }

    public int getID(){
        return this.userID;
    }

    public String getName(){
        return this.name;
    }

    public Quest getActiveQuest(){
        return this.activeQuest;
    }

    public void setActiveQuest(Quest quest){
        this.activeQuest = quest;
    }

    public void addCurrentQuest(Quest quest){
        this.quests.add(quest);
    }

    public ArrayList<Quest> getCurrentQuests(){
        return this.quests;
    }

    public ArrayList<Quest> getSolvedQuests(){
        return this.solvedquests;
    }
}
