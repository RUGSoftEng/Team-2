package com.mycompany.myapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruben on 23/02/2016.
 */
public abstract class Quest implements Serializable{
    protected String name;
    protected ArrayList<Landmark> landmarks = new ArrayList<>(), visitedLandmarks = new ArrayList<>();
    protected boolean isUserGenerated;
    protected int questID;
    protected String category;

    public Quest(int id, String name, boolean isUserGenerated){
        this.questID = id;
        this.name = name;
        this.isUserGenerated = isUserGenerated;
    }

    public abstract boolean isInOrder();

    @Override
    public String toString() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getID(){
       return this.questID;
    }

    //for getting progress we don't need the variable (yet)
    public int getProgress(){
        if(landmarks.isEmpty()){
            return 100;
        }
        return (100 * (visitedLandmarks.size() / landmarks.size()));
    }



    public ArrayList<Landmark> getLandmarks(){
        return this.landmarks;
    }

    public ArrayList<Landmark> getVisitedLandmarks(){
        return this.visitedLandmarks;
    }

    public void addLandmark(Landmark landmark){ this.landmarks.add(landmark);  }

    public void addLandmarkList(Landmark[] array) {
        for (Landmark landmark : array) this.landmarks.add(landmark);
    }

    public void isCompleted(Landmark landmark){ //finish landmark based on object
        this.visitedLandmarks.add(landmark);
        this.landmarks.remove(landmark); //TODO: now slow might be faster
    }

    public void isCompleted(int landmarkID){ //finish landmark based on id
        for(Landmark l : visitedLandmarks){
            if(landmarkID == l.getID()){
                this.visitedLandmarks.add(l);
                this.landmarks.remove(l); //TODO: now slow might be faster
                break;
            }
        }

    }


    //TODO should check for string name and other possibly unwanted user input
    //True is OK, False is a incorrect Quest
    public boolean validate(){ return true; };
}