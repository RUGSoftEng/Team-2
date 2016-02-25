package com.example.ruben.softwareengeneering20;

import java.util.ArrayList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Question {
    private int points, answer;
    private String question;
    private ArrayList<String> possibleAnswers;
    private Landmark landmark;

    public boolean checkAnswer(int givenAnswer){ return givenAnswer == answer;    }
}
