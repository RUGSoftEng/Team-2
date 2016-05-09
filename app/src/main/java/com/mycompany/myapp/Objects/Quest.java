package com.mycompany.myapp.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here.
 *
 * Created by Ruben on 23/02/2016.
 */
public abstract class Quest implements Serializable{
    protected String name; //field description goes here
    protected ArrayList<Landmark> landmarks = new ArrayList<>(); //field description goes here
    protected ArrayList<Landmark> visitedLandmarks = new ArrayList<>(); //field description goes here
    protected boolean isUserGenerated; //field description goes here
    protected String questID; //field description goes here
    protected int totallandmarks; //field description goes here
    protected String category; //field description goes here

    /* Method description goes here. */
    public Quest(String id, String name, boolean isUserGenerated){
        this.questID = id;
        this.name = name;
        this.isUserGenerated = isUserGenerated;
        this.totallandmarks = 0;
    }

    public abstract boolean isInOrder();

    /* Method description goes here. */
    @Override
    public String toString() {
        return this.getName();
    }

    /* Method description goes here. */
    public void setName(String name){
        this.name = name;
    }

    /* Method description goes here. */
    public String getID(){
       return this.questID;
    }

    /* Method description goes here. */
    public String getName() { return name; }

    /* For getting progress we don't need the variable (yet). */
    public int getProgress(){
        if(landmarks.isEmpty()){
            return 100;
        }
        return ((100 * visitedLandmarks.size()) / totallandmarks);
    }


    /* Method description goes here. */
    public ArrayList<Landmark> getLandmarks(){
        return this.landmarks;
    }

    /* Method description goes here. */
    public ArrayList<Landmark> getVisitedLandmarks(){
        return this.visitedLandmarks;
    }

    /* Method description goes here. */
    public void addLandmark(Landmark landmark){
        this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + 1;
    }

    /* Method description goes here. */
    public void addLandmarkList(List<Landmark> list) {
        for (Landmark landmark : list) this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + list.size();
    }

    /* Method description goes here. */
    public void isCompleted(Landmark landmark){ //finish landmark based on object
        this.visitedLandmarks.add(landmark);
        this.landmarks.remove(landmark); //TODO: now slow might be faster
    }

    /* Method description goes here. */
    public void isCompleted(String landmarkID){ //finish landmark based on id
        for(Landmark l : visitedLandmarks){
            if(landmarkID == l.getID()){
                this.visitedLandmarks.add(l);
                this.landmarks.remove(l); //TODO: now slow might be faster
                break;
            }
        }

    }


    //TODO should check for string name and other possibly unwanted user input
    /* True is OK, False is a incorrect Quest. */
    public boolean validate(){ return true; };
}