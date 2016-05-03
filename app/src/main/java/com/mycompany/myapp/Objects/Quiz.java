package com.mycompany.myapp.Objects;

import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Quiz {
    private int points;  //index of possibleAnswer is answer
    private String question;
    private String answer;
    private String[] possibleAnswers;

    public Quiz(String question){
        this.question = question;
    }

    public boolean Quiz(String givenAnswer){ return givenAnswer == answer;    }

    public void setAnswers(String[] answers){
        this.possibleAnswers = answers;
    }

    public void setRightAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer(){
        return  this.answer;
    }

    public String getQuestion(){
        return  this.question;
    }

    public String[] getPossibleAnswers(){
        return this.possibleAnswers;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }
}
