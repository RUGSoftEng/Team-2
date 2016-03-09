package com.mycompany.myapp;

/**
 * Created by Ruben on 09/03/2016.
 */
public class AreaQuest extends Quest {

    //TODO: add parameters: startingPoint(MapsLocation), height(in MapUnit's (so blocks)), width(same unit as height);

    private float height, width;
    private int currentLandmark = 0;

    public AreaQuest(String name, boolean isUserGenerated) {
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



    /* TODO: Useful, if decided we make Area's as squares
    public void setArea(startingPoint, float height, float width){
        this.startingPoint = startingPoint;
        this.height = height;
        this.width = width;
    }

    */


}
