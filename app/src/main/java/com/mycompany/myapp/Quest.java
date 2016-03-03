package com.mycompany.myapp;

import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public abstract class Quest {
    protected String name;
    protected ArrayList<Landmark> landmarks, visitedLandmarks;
    protected boolean isUserGenerated;
    protected int progress;

    public Quest(String name, boolean isUserGenerated){
        this.name = name;
        this.isUserGenerated = isUserGenerated;
    }


    public void setName(String name){
        this.name = name;
    }

    //for getting progress we don't need the variable (yet)
    public int getProgress(){
        return (100/(landmarks.size() / visitedLandmarks.size()));
    }



    //Adding landmarks by object now, could be changed to adding(creating) by name?

    public void addLandmark(Landmark landmark){ this.landmarks.add(landmark);  }

    public void isCompleted(Landmark landmark){
        this.landmarks.add(landmark);
    }


    //TODO should check for string name and other possibly unwanted user input
    //True is OK, False is a incorrect Quest
    public boolean validate(){ return true; };
}