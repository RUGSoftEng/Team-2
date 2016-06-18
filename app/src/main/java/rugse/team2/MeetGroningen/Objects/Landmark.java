package rugse.team2.MeetGroningen.Objects;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.parse.Parse;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * This class represents a landmark, which is an easily recognisable and interesting building
 * or object of a landscape or town, in this case of the city of Groningen, the Netherlands.
 *
 * Created by Ruben on 23-02-2016.
 */
public class Landmark implements Serializable{
    //private ArrayList<Quiz> questions = new ArrayList<Quiz>(); TODO not yet used so not needed yet (could bring serialization errors)

    /** the name of the landmark */
    private String name;
    /** the landmark's location's name, i.e. of the street or neighbourhood where the landmark is located */
    private String locationName;
    /** the landmark's description, i.e. general information about the landmark, usually including its use and history */
    private String information;
    /** the landmark's unique ID */
    private String landmarkID;
    /** the amount of points this landmark is worth to a user reaching it */
    private int points = 10;
    /** this landmark's latitude, i.e. its vertical coordinate on the globe */
    private double lat;
    /** this landmark's longitude, i.e. its horizontal coordinate on the globe */
    private double lng;

    /** boolean which indicates whether the landmark is user-generated (true) or not (false, default) */
    private boolean isUserGenerated = false;
    /** boolean which indicates whether the landmark has been sent already (true) or not (false, default) */
    private boolean isSend = false;

    //Quiz functionality

    /** the question for a multiple choice quiz */
    private String question;
    /** the correct answer for a multiple choice quiz */
    private String answer;
    /** the available answers for a multiple choice quiz */
    private String[] possibleAnswers;


    /** Constructor which initialises the landmark with its ID and name. */
    public Landmark(String name, String id){
        this.landmarkID = id;
        this.name = name;
    }

    /** Constructor which initialises the landmark with its ID, name, location name, worth, description, and location. */
    public Landmark(String name, String id, String locationName, int points, String information, double lat, double lng){
        this.landmarkID = id;
        this.name = name;
        this.locationName = locationName;
        this.points = points;
        this.information = information;
        this.lat = lat;
        this.lng = lng;
    }

    /** Makes sure that the textual representation of a landmark is simply its name. */
    @Override
    public String toString() {
        return this.name;
    }

    //lots of getters and setters (+ add, delete) below

    /** Getter method for the location (latitude + longitude) in LatLng format. */
    public LatLng getLocation(){
        return new LatLng(lat, lng);
    }

    /** Getter method for the location (latitude + longitude) in Location format. */
    public Location getLocationObject() {
        Location loc = new Location("location");
        loc.setLatitude(lat);
        loc.setLongitude(lng);
        return loc;
    }


    /** Setter method for the location, which accepts a LatLng object. */
    public void setLocation(LatLng location){
        this.lat = location.latitude;
        this.lng = location.longitude;
    }

    /** Setter method for the location, which accepts latitude and longitude as distinct doubles. */
    public void setLocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }


    /** Getter method for the landmark's ID. */
    public String getID(){
        return this.landmarkID;
    }

    /** Setter method for the location name. */
    public void setLocationName(String name){
        this.locationName = name;
    }

    /** Getter method for the location name. */
    public String getLocationName(){
        return this.locationName;
    }

    /** Setter method for the landmark's name. */
    public void setName(String name){
        this.name = name;
    }

    /** Getter method for the landmark's name. */
    public String getName(){
        return this.name;
    }

    /** Setter method for the description. */
    public void setInformation(String info){
        this.information = info;
    }

    /** Getter method for the description. */
    public String getInformation(){
        return this.information;
    }

    /** Setter method for amount of points worth. */
    public void setPoints(int points){
        this.points = points;
    }

    /** Getter method for amount of points worth. */
    public int getPoints(){
        return this.points;
    }

    /** Setter method for the user-generated flag. */
    public void setUserGenerated(boolean b){
        this.isUserGenerated = b;
    }

    /** Getter method for the user-generated flag. */
    public boolean isUserGenerated(){
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

    //Quiz methods

    /** Setter method for the multiple choice question. */
    public void setQuestion (String question) {
        this.question = question;
    }

    /** Getter method for the multiple choice question. */
    public String getQuestion() {
        return this.question;
    }

    /** Setter method for the correct multiple choice answer. */
    public void setAnswer (String answer) {
        this.answer = answer;
    }

    /** Getter method for the correct multiple choice answer. */
    public String getAnswer() {
        return this.answer;
    }

    /** Setter method for the available multiple choice answers. */
    public void setPossibleAnswers (String[] possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    /** Getter method for the available multiple choice answers. */
    public String[] getPossibleAnswers() {
        return this.possibleAnswers;
    }

    /** Setter method for a Quiz, which is multiple choice question with a few available answers, from which one is correct. */
    public void setQuiz(String question, String answer, String[] possibleAnswers) {
        this.question = question;
        this.answer = answer;
        this.possibleAnswers = possibleAnswers;
    }

    /** Method that puts the landmark on the parse server.
      * Values may be numerical, String, JSONObject, JSONArray, JSONObject.NULL, or other ParseObjects, but not null. */
    public void putOnServer(){
        ParseObject lm = new ParseObject("Landmarks");

        Gson gson = new Gson();
        lm.put("Object", gson.toJson(this));

        lm.put("UserGenerated", this.isUserGenerated);
        lm.put("Information", this.information);
        lm.put("Points", this.points);
        lm.put("Longitude", this.lng);
        lm.put("Latitude", this.lat);
        lm.put("Name", this.name);
        lm.put("ID", this.landmarkID);
        lm.saveInBackground();
    }

    public void putCustomOnServer(){
        ParseObject lm = new ParseObject("SendLandmarks");

        Gson gson = new Gson();
        lm.put("Object", gson.toJson(this));

        lm.put("UserGenerated", this.isUserGenerated);
        lm.put("Information", this.information);
        lm.put("Points", this.points);
        lm.put("Longitude", this.lng);
        lm.put("Latitude", this.lat);
        lm.put("Name", this.name);
        lm.put("ID", this.landmarkID);
        lm.saveInBackground();
    }
}