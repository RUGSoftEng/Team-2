package com.mycompany.myapp;

import java.security.PublicKey;

/**
 * Created by Ruben on 24/02/2016.
 */

// All names of tables and columns used in the landmark DATABASE
public class DBConstants {
    public static final String DATABASE_NAME = "Database";
    public static final String TABLE_NAME_LANDMARK = "landmarks_table";
    public static final String TABLE_NAME_QUEST = "quests_table";
    public static final int DATABASE_VERSION = 1;


    public static final String LANDMARK_ID = "landmark_ID";
    public static final String QUEST_ID = "quest_ID";


    public static final String QUEST = "quest_Object";
    public static final String LANDMARK = "landmark_Object";




    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_LANDMARK_ENTRIES =
            "CREATE TABLE " + DBConstants.TABLE_NAME_LANDMARK + " (" +
                    DBConstants.LANDMARK_ID + " INTEGER PRIMARY KEY," +
                    DBConstants.LANDMARK + BLOB_TYPE +
                    " )";

    public static final String SQL_CREATE_QUEST_ENTRIES =
            "CREATE TABLE " + DBConstants.TABLE_NAME_QUEST + " (" +
                    DBConstants.QUEST_ID + " INTEGER PRIMARY KEY," +
                    DBConstants.QUEST + BLOB_TYPE +
                    " )";

    //delete all entries, now landmark table
    public static final String SQL_DELETE_lANDMARK_ENTRIES =
            "DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME_LANDMARK;

    public static final String SQL_DELETE_QUEST_ENTRIES =
            "DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME_LANDMARK;

}
