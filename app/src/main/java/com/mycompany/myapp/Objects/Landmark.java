package com.mycompany.myapp.Objects;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Class description goes here.
 *
 * Created by Ruben on 23/02/2016.
 */
public class Landmark implements Serializable{



    private Quiz quiz; //field description goes here
    //private ArrayList<Quiz> questions = new ArrayList<Quiz>(); TODO not yet used so not need yet(could bring errors serialization errors)
    private String name; //field description goes here
    private String locationName; //locationName == streetName/neighbourhood
    private String information; //field description goes here
    private String landmarkID; //field description goes here
    private int points = 10; //field description goes here
    //private ArrayList<Question> questions = new ArrayList<Question>(); TODO not yet used so not need yet(could bring errors serialization errors)
    private double lat; //field description goes here
    private double lng; //field description goes here

    /* Method description goes here. */
    public Landmark(String name, String id){
        this.landmarkID = id;
        this.name = name;
    }

    /* Method description goes here. */
    public Landmark(String name, String id, String locationName, int points, String information, double lat, double lng){
        this.landmarkID = id;
        this.name = name;
        this.locationName = locationName;
        this.points = points;
        this.information = information;
        this.lat = lat;
        this.lng = lng;
    }

    /* For printing as listItem, now only printing name of landmark. */
    @Override
    public String toString() {
        return this.name;
    }

    //lots of getters and setters(+ add, delete) below

    /* Method description goes here. */
    public LatLng getLocation(){
        return new LatLng(lat, lng);
    }

    /* Method description goes here. */
    public Location getLocationObject() {
        Location loc = new Location("location");
        loc.setLatitude(lat);
        loc.setLongitude(lng);
        return loc;
    }

    /* Method description goes here. */
    public Quiz getQuiz() { return this.quiz; }

    /* Method description goes here. */
    public void setLocation(LatLng location){
        this.lat = location.latitude;
        this.lng = location.longitude;
    }

    /* Method description goes here. */
    public void setLocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    /* Method description goes here. */
    public void setQuiz(Quiz q) {
        this.quiz = q;
    }

    /* Method description goes here. */
    public String getID(){
        return this.landmarkID;
    }

    /* Method description goes here. */
    public void setLocationName(String name){
        this.locationName = name;
    }

    /* Method description goes here. */
    public String getLocationName(){
        return this.locationName;
    }

    /* Method description goes here. */
    public void setName(String name){
        this.name = name;
    }

    /* Method description goes here. */
    public String getName(){
        return this.name;
    }

    /* Method description goes here. */
    public void setInformation(String info){
        this.information = info;
    }

    /* Method description goes here. */
    public String getInformation(){
        return this.information;
    }

    /* Method description goes here. */
    public void setPoints(int points){
        this.points = points;
    }

    /* Method description goes here. */
    public int getPoints(){
        return this.points;
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
}