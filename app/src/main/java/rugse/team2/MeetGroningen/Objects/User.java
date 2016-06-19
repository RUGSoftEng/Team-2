package rugse.team2.MeetGroningen.Objects;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents an application user, which is a profile belonging to a real person
 * which includes their name, amount of obtained points, started quests, and completed quests.
 *
 * Created by Ruben on 23-02-2016.
 */
public class User implements Serializable {
    /** The amount of points the user has obtained up to now. */
    private int points = 0;
    /** The user's unique ID. */
    private String userID;
    /** The user's name. */
    private String name;

    /** The list of quests started but not yet completed by this user. */
    private ArrayList<Quest> currentQuests = new ArrayList<>();
    /** The list of quests already completed by this user. */
    private ArrayList<Quest> solvedQuests = new ArrayList<>();
    /** The currently active quest, which is the quest the user is on currently. */
    private Quest activeQuest;

    //private ArrayList<Achievement> achievements; TODO: not yet implemented

    /**
     * Constructor which stores a given user ID.
     *
     * @param userID The user ID.
     */
    public User(String userID) {
        this.userID = userID;
    }

    /**
     * Adds the given quest to the user's list of started quests.
     *
     * @param q The quest.
     */
    public void addQuest(Quest q) {
        this.currentQuests.add(q);
        Log.d("TEST", "Added a quest to currentQuest: " + q.toString());
    }

    /**
     * Adds the given quest to the user's list of completed quests after removing it from their list of started quests.
     *
     * @param q The quest.
     */
    public void finishQuest(Quest q){
        this.currentQuests.remove(q);
        this.solvedQuests.add(q);
        if(q == activeQuest){
            this.activeQuest = null;
        }
    }

    /**
     * Adds the given points value to the user's amount of points.
     *
     * @param i The points value.
     */
    public void addPoints(int i){
        this.points = this.points + i;
    }

    /** Getter method for the amount of points obtained. */
    public int getPoints(){
        return this.points;
    }

    /** Getter method for the user's ID. */
    public String getID(){
        return this.userID;
    }

    /** Getter method for the user's name. */
    public String getName(){
        return this.name;
    }

    /** Getter method for the list of started but not yet completed quests. */
    public ArrayList<Quest> getCurrentQuests() {
        return currentQuests;
    }

    /** Getter method for the list of already completed quests. */
    public ArrayList<Quest> getSolvedQuests() { return solvedQuests; }

    /** Setter method for the currently active quest. */
    public void setActiveQuest(Quest q) {
        this.activeQuest = q;
    }

    /** Getter method for the currently active quest. */
    public Quest getActiveQuest(){
        return activeQuest;
    }

}