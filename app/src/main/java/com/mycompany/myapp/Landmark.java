package com.mycompany.myapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Landmark {

    //locationName == streetName/neighbourhood
    private String name, locationName, information;
    private int points, landmarkID;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private LatLng location;


    public Landmark(String name, int id){
        this.landmarkID = id;
        this.name = name;
    };


    public Landmark(String name, int id, String locationName, int points, String information, LatLng location){
        this.landmarkID = id;
        this.name = name;
        this.locationName = locationName;
        this.points = points;
        this.information = information;
        this.location = location;
    };



    //For printing as listItem, now only printing name of landmark
    @Override
    public String toString() {
        return this.name;
    }

    //lots of getters and setters(+ add, delete) below

    public LatLng getLocation(){
        return this.location;
    }

    public void setLocation(LatLng location){
        this.location = location;
    }

    public int getID(){
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


    public void addQuestion(Question q){
        this.questions.add(q);
    }

    public Question getQuestion(int i){
        return this.questions.get(i);
    }

    public void removeQuestion(Question q){
        this.questions.remove(q);
    }

}