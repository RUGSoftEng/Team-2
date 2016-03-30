package com.mycompany.myapp;

import java.util.ArrayList;

/**
 * Created by Ruben on 18/03/2016.
 */
public class Initializer {

    private int assignedID = 1; //counter to assign initializing id's (starts at 1)
    private ArrayList<Landmark> landmarks = new ArrayList<Landmark>();
    private ArrayList<Quest> quests = new ArrayList<Quest>();

    //creates all standard landmarks
    public ArrayList<Landmark> createStandardLandmarks(){

        //number ID 1
        Landmark martiniToren = new Landmark("Martini Toren", asignID());
        martiniToren.setLocation(53.219383, 6.568125);
        landmarks.add(martiniToren);

        //number 2
        Landmark aKerk = new Landmark("A Kerk", asignID());
        aKerk.setLocation(53.216498, 6.562386);
        landmarks.add(aKerk);

        //number 3
        Landmark gronigenMuseum = new Landmark("Groningen Museum", asignID());
        gronigenMuseum.setLocation(53.212292, 6.565761);
        landmarks.add(gronigenMuseum);


        //create all standard quests, TODO: we could also move this to createAllStandardQuests() and get from landmark list(however hardcoded number)

        //number 1
        ExactQuest essentailQuest = new ExactQuest(asignID(), "Essential", false);
        essentailQuest.addLandmark(martiniToren);
        essentailQuest.addLandmark(aKerk);
        essentailQuest.addLandmark(gronigenMuseum);
        quests.add(essentailQuest);


        //number 2
        ExactQuest reversedEssential = new ExactQuest(asignID(), "RevEssentail", false);
        reversedEssential.addLandmark(gronigenMuseum);
        reversedEssential.addLandmark(aKerk);
        reversedEssential.addLandmark(martiniToren);
        quests.add(reversedEssential);

        return landmarks;
    }

    public ArrayList<Quest> createStandardQuests(){
        return quests;
    }

    public User createStandardUser() { return new User(asignID()); }

    private int asignID(){
        return this.assignedID++;
    }
}


