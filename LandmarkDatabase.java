package com.example.ruben.softwareengeneering20;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ruben on 24/02/2016.
 */
public class LandmarkDatabase {

    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBConstants.TABLE_NAME + " (" +
                    DBConstants.LANDMARK_ID + " INTEGER PRIMARY KEY," +
                    DBConstants.LANDMARK + BLOB_TYPE + COMMA_SEP +
            " )";

    //delete all entries, now landmark table
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME;



    /* If we don't manage to store object

     private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBConstants.TABLE_NAME + " (" +
                    DBConstants.LANDMARK_ID + " INTEGER PRIMARY KEY," +
                    DBConstants.LANDMARK_LOCATION + TEXT_TYPE + COMMA_SEP +
                    DBConstants.LANDMARK_NAME + TEXT_TYPE + COMMA_SEP +
                    DBConstants.LANDMARK_POINTS + TEXT_TYPE + COMMA_SEP +
                    DBConstants.LANDMARK_INFORMATION + TEXT_TYPE + COMMA_SEP +
            " )";

     */
}
