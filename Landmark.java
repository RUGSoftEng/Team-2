package com.example.ruben.softwareengeneering20;

import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Landmark {

    //locationName == streetName/neighbourhood
    private String name, locationName, information;
    private int points, landmarkID;
    private ArrayList<Question> questions;

    //this is probably wrong needs to be changed to the google maps marker/location object
   // private location location;

    public Landmark(String name, int id){
        this.landmarkID = id;
        this.name = name;
    };




    //lots of getters and setters(+ add, delete) below

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