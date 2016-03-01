package com.mycompany.myapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class User implements Serializable {

    private int userID, points;
    private String name;

    private ArrayList<Achievement> achievements;
    private ArrayList<Quest> quests, solvedquests;
    private Quest activeQuest;

    public void addQuest(Quest q) { this.quests.add(q);    }

}
