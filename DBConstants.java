package com.example.ruben.softwareengeneering20;

/**
 * Created by Ruben on 24/02/2016.
 */

// All names of tables and columns used in the landmark DATABASE
public class DBConstants {
    public static final String DATABASE_NAME = "landmarks_database";
    public static final String TABLE_NAME = "landmarks_table";
    public static final int DATABASE_VERSION = 1;


    public static final String LANDMARK_ID = "landmark_ID";

    public static final String LANDMARK = "landmark_Object";



    //TODO: maybe add serialization of landmark object here. (to byte string), otherwise implement questions


    /* Unfinished declaration of tables, might not be needed if we serialize the landmark object (key is landmark_ID)


    /* Not needed if we manage to serialize the landmark object
    public static final String LANDMARK_NAME = "landmark_Name";
    public static final String LANDMARK_LOCATION = "landmark_Location";
    public static final String LANDMARK_INFORMATION = "landmark_Information";
    public static final String LANDMARK_POINTS = "landmark_Points";
    */


    /* for the list of questions that each landmark contains
    public static final String QUESTION_LANDMARK_ID = "landmark_ID";
    public static final String QUESTION_RANSWER = "question_Answer";
    public static final String QUESTION_PANSWER = "question_Option";
    */
}
