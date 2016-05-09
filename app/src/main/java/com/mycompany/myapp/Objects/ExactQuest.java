package com.mycompany.myapp.Objects;

/**
 * Class description goes here.
 *
 * Created by Ruben on 28/02/2016.
 */
public class ExactQuest extends Quest {

    //private int currentLandmark = 0; Probably not needed

    /* Method description goes here. */
    public ExactQuest(String id, String name, boolean isUserGenerated) {
        super(id, name, isUserGenerated);
    }

    /* Method description goes here. */
    public boolean isInOrder(){
        return true;
    }

/* BELOW probably not needed
    public void solvedLandmark(){
        this.currentLandmark++;
    }


    public Landmark getLandmark(int i){
        return this.landmarks.get(i);
    }

    public Landmark getCurrentLandmark(){
        return this.landmarks.get(this.currentLandmark);
    }
    */
}
