package com.mycompany.myapp.Objects;

import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Question {
    private int points, answer;  //index of possibleAnswer is answer
    private String question;
    private ArrayList<String> possibleAnswers;
    private Landmark landmark;




    public Question(String question, Landmark l){
        this.question = question;
        this.landmark = l;
    }

    public boolean checkAnswer(int givenAnswer){ return givenAnswer == answer;    }

    public void setAnswer(String answer){
        this.possibleAnswers.add(answer);
        this.answer = possibleAnswers.size()-1;
    }

    public int getAnswer(){
        return  this.answer;
    }

    public String getQuestion(){
        return  this.question;
    }

    public String getPossibleAnswer(int i){
        return  this.possibleAnswers.get(i);
    }

    public void setPoints(int points){
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }

    public Landmark getLandmark(){
        return this.landmark;
    }
}
