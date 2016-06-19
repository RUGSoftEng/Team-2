package rugse.team2.MeetGroningen.Objects;

import com.google.gson.Gson;
import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a quest, which is a series of landmarks with an overarching theme
 * that forms a route for a user to follow to get to know a specific side of Groningen.
 *
 * Created by Ruben on 23-02-2016.
 */
public abstract class Quest implements Serializable {
    /** The name of the quest. */
    protected String name;
    /** The list of landmarks within the quest not yet visited. */
    protected ArrayList<Landmark> landmarks = new ArrayList<>();
    /** The list of landmarks within the quest already visited. */
    protected ArrayList<Landmark> visitedLandmarks = new ArrayList<>();
    /** The quest's unique ID */
    protected String questID;
    /** The total amount of landmarks within the quest. */
    protected int totallandmarks;
    /** The category the quest falls under. */
    protected String category;

    /** A boolean which indicates whether the quest is user-generated (true) or not (false). */
    protected boolean isUserGenerated;
    /** A boolean which indicates whether the quest has been sent already (true) or not (false, default). */
    protected boolean isSend = false;

    /**
     * Constructor which initialises the at this point still empty quest with its ID, name, and user-generated flag.
     *
     * @param id The quest's ID.
     * @param name The quest's name.
     * @param isUserGenerated The quest's user-generated flag, which is True for custom quests and False for standard quests.
     */
    public Quest(String id, String name, boolean isUserGenerated) {
        this.questID = id;
        this.name = name;
        this.isUserGenerated = isUserGenerated;
        this.totallandmarks = 0;
    }

    /** An abstract method, which should return whether or not the subclass's landmarks have to be visited in order. */
    public abstract boolean isInOrder();

    /**
     * Makes sure that the textual representation of a quest is simply its name.
     *
     * @return The name of this quest as a String object.
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /** Getter method for the quest's ID. */
    public String getID(){
        return this.questID;
    }

    /** Setter method for the quest's name. */
    public void setName(String name){
        this.name = name;
    }

    /** Getter method for the quest's name. */
    public String getName() {
        return name;
    }

    /**
     * Gets the percentage of the quest completed so far, measured in percentage of its landmarks visited already.
     *
     * @return The percentage of the quest completed so far.
     */
    public int getProgress(){ //for getting progress we don't need the variable (yet)
        if(landmarks.isEmpty()){
            return 100;
        }
        return ((100 * visitedLandmarks.size()) / totallandmarks);
    }


    /** Getter method for the list of not yet visited landmarks. */
    public ArrayList<Landmark> getLandmarks(){
        return this.landmarks;
    }

    /** Getter method for the list of already visited landmarks. */
    public ArrayList<Landmark> getVisitedLandmarks(){
        return this.visitedLandmarks;
    }

    /**
     * Adds the given landmark to the quest's landmarks and increments the amount of landmarks within it.
     *
     * @param landmark The landmark.
     */
    public void addLandmark(Landmark landmark){
        this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + 1;
    }

    /**
     * Adds all landmarks in the given list to the quest's landmarks and
     * increases the amount of landmarks within it with the list's size.
     *
     * @param list The List object containing the landmarks.
     */
    public void addLandmarkList(List<Landmark> list) {
        for (Landmark landmark : list) this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + list.size();
    }

    /**
     * Adds the given landmark to the list of already visited landmarks
     * and removes it from the list of not yet visited landmarks.
     *
     * @param landmark The landmark.
     */
    public void isCompleted(Landmark landmark){ //finish landmark based on object
        this.visitedLandmarks.add(landmark);
        this.landmarks.remove(landmark); //TODO: now slow, could possibly be faster
    }

    /**
     * Adds the landmark corresponding to the given ID to the list of already visited
     * landmarks and removes it from the list of not yet visited landmarks.
     *
     * @param landmarkID The ID corresponding to the landmark.
     */
    public void isCompleted(String landmarkID) { //finish landmark based on ID
        for(Landmark l : visitedLandmarks){
            if(landmarkID == l.getID()){
                this.visitedLandmarks.add(l);
                this.landmarks.remove(l); //TODO: now slow might be faster
                break;
            }
        }

    }

    //TODO should check for string name and other possibly unwanted user input
    /** Returns whether the quest is a valid quest (True) or not (False). */
    public boolean validate() {
        return true;
    };

    /** Getter method for the user-generated flag. */
    public boolean isUserGenerated () {
        return this.isUserGenerated;
    }

    /** Setter method for the is-send flag. */
    public void setIsSend(boolean b) {
        this.isSend = b;
    }

    /** Getter method for the is-send flag. */
    public boolean isSend() {
        return this.isSend;
    }

    /**
     * Test toJSON method to try and serialise a landmark list.
     *
     * @param lijst The landmark list.
     * @return The serialised JSON representation of the landmark list.
     */
    public String toJSON(ArrayList<Landmark> lijst) {
        Gson gson = new Gson();
        String json = gson.toJson(lijst);
        return json;

        /*
        JSONObject landmarkList = new JSONObject();
        try {
            for(Landmark l : lijst){
                landmarkList.put("landmarkID", l.getID());
                landmarkList.put("landmark", l);
            }
            return landmarkList.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
        */
    }
    //end test toJSON

    /** Method that puts the quest on the parse server. Values may be numerical, String,
      * JSONObject, JSONArray, JSONObject.NULL, or other ParseObjects, but not null. */
    public void putOnServer(){ //TODO: find way to store the arrays of landmarks. JSON?
        ParseObject qst = new ParseObject("Quests");
        qst.put("UserGenerated", this.isUserGenerated);
        qst.put("Name", this.name);
        qst.put("ID", this.questID);
        qst.put("LandMarks", toJSON(landmarks));
        qst.saveInBackground();
    }

    /** Method that puts the quest on the parse server. Values may be numerical, String,
      * JSONObject, JSONArray, JSONObject.NULL, or other ParseObjects, but not null. */
    public void putCustomOnServer(){
        ParseObject qst = new ParseObject("SendQuests");
        qst.put("UserGenerated", this.isUserGenerated);
        qst.put("Name", this.name);
        qst.put("ID", this.questID);
        qst.put("LandMarks", toJSON(landmarks));
        qst.saveInBackground();
    }



}