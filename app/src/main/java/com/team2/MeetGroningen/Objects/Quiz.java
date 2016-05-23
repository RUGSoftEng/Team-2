package com.team2.MeetGroningen.Objects;

/**
 * This class represents a quiz, which is a single multiple choice question.
 * A quiz belongs to a landmark and can be done when reaching it. For fun,
 * to learn even more about the landmark, and to possibly receive more points.
 *
 * Created by Ruben on 23-02-2016.
 */
public class Quiz {
    private int points; //the amount of points the quiz is worth
    private String question; //the question for the quiz
    private String answer; //the correct answer for the quiz
    private String[] possibleAnswers; //the list of all possible answers, including the correct answer

    /* Constructor which stores a given question. */
    public Quiz(String question){
        this.question = question;
    }

    /* Returns whether a given answer is correct (true) or not (false). */
    public boolean Quiz(String givenAnswer){ return givenAnswer == answer;    }

    /* Setter method for the list of possible answers. */
    public void setAnswers(String[] answers){
        this.possibleAnswers = answers;
    }

    /* Setter method for the correct answer. */
    public void setRightAnswer(String answer) {
        this.answer = answer;
    }

    /* Getter method for the correct answer. */
    public String getAnswer(){
        return  this.answer;
    }

    /* Getter method for the question. */
    public String getQuestion(){
        return  this.question;
    }

    /* Getter method for the list of possible answers. */
    public String[] getPossibleAnswers(){
        return this.possibleAnswers;
    }

    /* Setter method for the amount of points worth. */
    public void setPoints(int points){
        this.points = points;
    }

    /* Getter method for the amount of points worth. */
    public int getPoints(){
        return this.points;
    }
}
