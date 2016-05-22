package com.mycompany.MeetGroningen.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a quest, which is a series of landmarks with an overarching theme,
 * that forms a route for a user to follow to get to know a specific side of Groningen.
 *
 * Created by Ruben on 23-02-2016.
 */
public abstract class Quest implements Serializable{
    protected String name; //the name of the quest
    protected ArrayList<Landmark> landmarks = new ArrayList<>(); //the list of landmarks within the quest not yet visited
    protected ArrayList<Landmark> visitedLandmarks = new ArrayList<>(); //the list of landmarks within the quest already visited
    protected boolean isUserGenerated; //an origin boolean indicating whether the quest is user-created (true) or standard (false)
    protected String questID; //the quest's unique ID
    protected int totallandmarks; //the total amount of landmarks within the quest
    protected String category; //the category this quest falls under

    /* Constructor which initialises the at this point still empty quest with its ID, name, and origin boolean. */
    public Quest(String id, String name, boolean isUserGenerated){
        this.questID = id;
        this.name = name;
        this.isUserGenerated = isUserGenerated;
        this.totallandmarks = 0;
    }

    /* An abstract method, which should return whether or not the subclass's landmarks have to be visited in order. */
    public abstract boolean isInOrder();

    /* Makes sure that the textual representation of a quest is simply its name. */
    @Override
    public String toString() {
        return this.getName();
    }

    /* Setter method for the quest's name. */
    public void setName(String name){
        this.name = name;
    }

    /* Getter method for the quest's ID. */
    public String getID(){
       return this.questID;
    }

    /* Getter method for the quest's name. */
    public String getName() { return name; }

    /* Returns the percentage of the quest completed so far, measured in percentage of its landmarks visited. */
    public int getProgress(){ //for getting progress we don't need the variable (yet)
        if(landmarks.isEmpty()){
            return 100;
        }
        return ((100 * visitedLandmarks.size()) / totallandmarks);
    }


    /* Getter method for the list of not yet visited landmarks. */
    public ArrayList<Landmark> getLandmarks(){
        return this.landmarks;
    }

    /* Getter method for the list of already visited landmarks. */
    public ArrayList<Landmark> getVisitedLandmarks(){
        return this.visitedLandmarks;
    }

    /* Adds the given landmark to the quest's landmarks and increments the amount of landmarks within it. */
    public void addLandmark(Landmark landmark){
        this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + 1;
    }

    /* Adds all landmarks in the given list to the quest's landmarks and
     * increases the amount of landmarks within it with the list's size. */
    public void addLandmarkList(List<Landmark> list) {
        for (Landmark landmark : list) this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + list.size();
    }

    /* Adds the given landmark to the list of already visited landmarks
     * and removes it from the list of not yet visited landmarks. */
    public void isCompleted(Landmark landmark){ //finish landmark based on object
        this.visitedLandmarks.add(landmark);
        this.landmarks.remove(landmark); //TODO: now slow might be faster
    }

    /* Adds the landmark corresponding to the given ID to the list of already visited
     * landmarks and removes it from the list of not yet visited landmarks. */
    public void isCompleted(String landmarkID){ //finish landmark based on ID
        for(Landmark l : visitedLandmarks){
            if(landmarkID == l.getID()){
                this.visitedLandmarks.add(l);
                this.landmarks.remove(l); //TODO: now slow might be faster
                break;
            }
        }

    }


    //TODO should check for string name and other possibly unwanted user input
    /* Returns whether the quest is a valid quest (true) or not (false). */
    public boolean validate(){ return true; };

    public boolean isUserGenerated (){
        return this.isUserGenerated;
    }
}