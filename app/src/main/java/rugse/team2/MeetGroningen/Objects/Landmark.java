package rugse.team2.MeetGroningen.Objects;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
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

    private Quiz quiz; //the quiz belonging to the landmark
    //private ArrayList<Quiz> questions = new ArrayList<Quiz>(); TODO not yet used so not needed yet (could bring serialization errors)
    private String name; //the name of the landmark
    private String locationName; //the landmark's location's name, i.e. of the street or neighbourhood where the landmark is located
    private String information; //the landmark's description, i.e. general information about the landmark, usually including its use and history
    private String landmarkID; //the landmark's unique ID
    private int points = 10; //the amount of points this landmark is worth to a user reaching it
    //private ArrayList<Question> questions = new ArrayList<Question>(); TODO not yet used so not needed yet (could bring errors serialization errors)
    private double lat; //this landmark's latitude, i.e. its vertical coordinate on the globe
    private double lng; //this landmark's longitude, i.e. its horizontal coordinate on the globe
    private boolean isUserGenerated = false; //default is false set to true if userGenerated

    /* Constructor which initialises the landmark with its ID and name. */
    public Landmark(String name, String id){
        this.landmarkID = id;
        this.name = name;
    }

    /* Constructor which initialises the landmark with its ID, name, location name, worth, description, and location. */
    public Landmark(String name, String id, String locationName, int points, String information, double lat, double lng){
        this.landmarkID = id;
        this.name = name;
        this.locationName = locationName;
        this.points = points;
        this.information = information;
        this.lat = lat;
        this.lng = lng;
    }

    /* Makes sure that the textual representation of a landmark is simply its name. */
    @Override
    public String toString() {
        return this.name;
    }

    //lots of getters and setters (+ add, delete) below

    /* Getter method for the location (latitude + longitude) in LatLng format. */
    public LatLng getLocation(){
        return new LatLng(lat, lng);
    }

    /* Getter method for the location (latitude + longitude) in Location format. */
    public Location getLocationObject() {
        Location loc = new Location("location");
        loc.setLatitude(lat);
        loc.setLongitude(lng);
        return loc;
    }

    /* Getter method for the quiz. */
    public Quiz getQuiz() { return this.quiz; }

    /* Setter method for the location, which accepts a LatLng object. */
    public void setLocation(LatLng location){
        this.lat = location.latitude;
        this.lng = location.longitude;
    }

    /* Setter method for the location, which accepts latitude and longitude as distinct doubles. */
    public void setLocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    /* Setter method for the quiz. */
    public void setQuiz(Quiz q) {
        this.quiz = q;
    }

    /* Getter method for the landmark's ID. */
    public String getID(){
        return this.landmarkID;
    }

    /* Setter method for the location name. */
    public void setLocationName(String name){
        this.locationName = name;
    }

    /* Getter method for the location name. */
    public String getLocationName(){
        return this.locationName;
    }

    /* Setter method for the landmark's name. */
    public void setName(String name){
        this.name = name;
    }

    /* Getter method for the landmark's name. */
    public String getName(){
        return this.name;
    }

    /* Setter method for the description. */
    public void setInformation(String info){
        this.information = info;
    }

    /* Getter method for the description. */
    public String getInformation(){
        return this.information;
    }

    /* Setter method for amount of points worth. */
    public void setPoints(int points){
        this.points = points;
    }

    /* Getter method for amount of points worth. */
    public int getPoints(){
        return this.points;
    }

    public void setUserGenerated(boolean b){
        this.isUserGenerated = b;
    }

    public boolean isUserGenerated(){
        return this.isUserGenerated;
    }

    /*
    public void addQuestion(Quiz q){
        this.questions.add(q);
    }

    public Quiz getQuestion(int i){
        return this.questions.get(i);
    }

    public void removeQuestion(Quiz q){
        this.questions.remove(q);
    }
    */

    /* method that puts the landmark on the parse server
    *  Values may be numerical, String, JSONObject, JSONArray, JSONObject.NULL, or other ParseObjects. value may not be null */
    public void putOnServer(){
        ParseObject lm = new ParseObject("Landmarks");
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