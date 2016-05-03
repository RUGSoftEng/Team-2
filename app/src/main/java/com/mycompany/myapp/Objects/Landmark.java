package com.mycompany.myapp.Objects;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Landmark implements Serializable{



    private String name, locationName, information; //locationName == streetName/neighbourhood
    private int points, landmarkID;
    private Quiz quiz;
    //private ArrayList<Quiz> questions = new ArrayList<Quiz>(); TODO not yet used so not need yet(could bring errors serialization errors)
    private String name, locationName, information, landmarkID; //locationName == streetName/neighbourhood
    private int points;
    //private ArrayList<Question> questions = new ArrayList<Question>(); TODO not yet used so not need yet(could bring errors serialization errors)
    private double lat, lng;


    public Landmark(String name, String id){
        this.landmarkID = id;
        this.name = name;
    };


    public Landmark(String name, String id, String locationName, int points, String information, double lat, double lng){
        this.landmarkID = id;
        this.name = name;
        this.locationName = locationName;
        this.points = points;
        this.information = information;
        this.lat = lat;
        this.lng = lng;
    };



    //For printing as listItem, now only printing name of landmark
    @Override
    public String toString() {
        return this.name;
    }

    //lots of getters and setters(+ add, delete) below

    public LatLng getLocation(){
        return new LatLng(lat, lng);
    }

    public Location getLocationObject() {
        Location loc = new Location("location");
        loc.setLatitude(lat);
        loc.setLongitude(lng);
        return loc;
    }

    public Quiz getQuiz() { return this.quiz; }

    public void setLocation(LatLng location){
        this.lat = location.latitude;
        this.lng = location.longitude;
    }

    public void setLocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public void setQuiz(Quiz q) {
        this.quiz = q;
    }

    public int getID(){

    public String getID(){
        return this.landmarkID;
    }

    public void setLocationName(String name){
        this.locationName = name;
    }

    public String getLocationName(){
        return this.locationName;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setInformation(String info){
        this.information = info;
    }


    public String getInformation(){
        return this.information;
    }

    public void setPoints(int points){
        this.points = points;
    }

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