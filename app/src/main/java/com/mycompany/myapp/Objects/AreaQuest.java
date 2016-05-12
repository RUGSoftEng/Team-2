package com.mycompany.myapp.Objects;

/**
 * This class represents an area quest, which is a kind of quest in which its
 * landmarks do not have to be visited in order, resulting in a somewhat free route.
 *
 * Created by Ruben on 09-03-2016.
 */
public class AreaQuest extends Quest {

    //TODO: add parameters: startingPoint(MapsLocation), height(in MapUnit's (so blocks)), width(same unit as height);

    private float height; //the height of the area in which the quest takes place
    private float width; //the width of the area in which the quest takes place
    private int currentLandmark = 0; //the amount of landmarks reached thus far within the quest

    /* Constructor which calls the superclass's constructor to initialise the quest. */
    public AreaQuest(String id, String name, boolean isUserGenerated) {
        super(id, name, isUserGenerated);
    }

    /* Returns that for this subclass of Quest, landmarks do not have to be visited in order. */
    public boolean isInOrder(){
        return false;
    }


/*

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
