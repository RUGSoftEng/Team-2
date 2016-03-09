package com.mycompany.myapp;

/**
 * Created by Ruben on 28/02/2016.
 */
public class ExactQuest extends Quest {

    private int currentLandmark = 0;

    public ExactQuest(String name, boolean isUserGenerated) {
        super(name, isUserGenerated);
    }




    public void solvedLandmark(){
        this.currentLandmark++;
    }


    public Landmark getLandmark(int i){
        return this.landmarks.get(i);
    }

    public Landmark getCurrentLandmark(){
        return this.landmarks.get(this.currentLandmark);
    }
}
