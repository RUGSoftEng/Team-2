package com.mycompany.myapp.Objects;

import java.util.ArrayList;

/**
 * Class description goes here.
 *
 * Created by Ruben on 23/02/2016.
 */
public class Quiz {
    private int points; //field description goes here
    private String question; //field description goes here
    private String answer; //field description goes here
    private String[] possibleAnswers; //index of possibleAnswer is answer

    /* Method description goes here. */
    public Quiz(String question){
        this.question = question;
    }

    /* Method description goes here. */
    public boolean Quiz(String givenAnswer){ return givenAnswer == answer;    }

    /* Method description goes here. */
    public void setAnswers(String[] answers){
        this.possibleAnswers = answers;
    }

    /* Method description goes here. */
    public void setRightAnswer(String answer) {
        this.answer = answer;
    }

    /* Method description goes here. */
    public String getAnswer(){
        return  this.answer;
    }

    /* Method description goes here. */
    public String getQuestion(){
        return  this.question;
    }

    /* Method description goes here. */
    public String[] getPossibleAnswers(){
        return this.possibleAnswers;
    }

    /* Method description goes here. */
    public void setPoints(int points){
        this.points = points;
    }

    /* Method description goes here. */
    public int getPoints(){
        return this.points;
    }
}
