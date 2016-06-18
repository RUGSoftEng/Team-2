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
    /** the name of the quest */
    protected String name;
    /** the list of landmarks within the quest not yet visited */
    protected ArrayList<Landmark> landmarks = new ArrayList<>();
    /** the list of landmarks within the quest already visited */
    protected ArrayList<Landmark> visitedLandmarks = new ArrayList<>();
    /** the quest's unique ID */
    protected String questID;
    /** the total amount of landmarks within the quest */
    protected int totallandmarks;
    /** the category the quest falls under */
    protected String category;

    /** boolean which indicates whether the quest is user-generated (true) or not (false, default) */
    protected boolean isUserGenerated;
    /** boolean which indicates whether the quest has been sent already (true) or not (false, default) */
    protected boolean isSend = false;

    /** Constructor which initialises the at this point still empty quest with its ID, name, and origin boolean. */
    public Quest(String id, String name, boolean isUserGenerated){
        this.questID = id;
        this.name = name;
        this.isUserGenerated = isUserGenerated;
        this.totallandmarks = 0;
    }

    /** An abstract method, which should return whether or not the subclass's landmarks have to be visited in order. */
    public abstract boolean isInOrder();

    /** Makes sure that the textual representation of a quest is simply its name. */
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
    public String getName() { return name; }

    /** Returns the percentage of the quest completed so far, measured in percentage of its landmarks visited already. */
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

    /** Adds the given landmark to the quest's landmarks and increments the amount of landmarks within it. */
    public void addLandmark(Landmark landmark){
        this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + 1;
    }

    /** Adds all landmarks in the given list to the quest's landmarks and
      * increases the amount of landmarks within it with the list's size. */
    public void addLandmarkList(List<Landmark> list) {
        for (Landmark landmark : list) this.landmarks.add(landmark);
        this.totallandmarks = totallandmarks + list.size();
    }

    /** Adds the given landmark to the list of already visited landmarks
      * and removes it from the list of not yet visited landmarks. */
    public void isCompleted(Landmark landmark){ //finish landmark based on object
        this.visitedLandmarks.add(landmark);
        this.landmarks.remove(landmark); //TODO: now slow might be faster
    }

    /** Adds the landmark corresponding to the given ID to the list of already visited
      * landmarks and removes it from the list of not yet visited landmarks. */
    public void isCompleted(String landmarkID){ //finish landmark based on ID
        for(Landmark l : visitedLandmarks){
            if(landmarkID == l.getID()){
                this.visitedLandmarks.add(l);
                this.landmarks.remove(l); //TODO: now slow might be faster
                break;
            }
        }

    }


    //TODO should check for string name and other possibly unwanted user input
    /** Returns whether the quest is a valid quest (true) or not (false). */
    public boolean validate(){ return true; };

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

    /** Test toJSON method to try and serialize a landmark list. */
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