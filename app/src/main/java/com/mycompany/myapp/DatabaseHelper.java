package com.mycompany.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ruben on 27/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.

        public DatabaseHelper(Context context) {
            super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBConstants.SQL_CREATE_LANDMARK_ENTRIES);
            db.execSQL(DBConstants.SQL_CREATE_QUEST_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(DBConstants.SQL_DELETE_lANDMARK_ENTRIES);
            db.execSQL(DBConstants.SQL_DELETE_QUEST_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        public void putLandmarkInformation(DatabaseHelper helper, int landmarkID, byte[] object){
            SQLiteDatabase sq = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.LANDMARK_ID, landmarkID);
            cv.put(DBConstants.LANDMARK, object);
            sq.insert(DBConstants.TABLE_NAME_LANDMARK, null, cv);
            Log.d("COMMENT", "Tried putting landmark in database");
        }

        public void putQuestInformation(DatabaseHelper helper, int questID, byte[] object){
            SQLiteDatabase sq = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.QUEST_ID, questID);
            cv.put(DBConstants.QUEST, object);
            sq.insert(DBConstants.TABLE_NAME_QUEST, null, cv);
            Log.d("COMMENT", "Tried putting quest in database");
        }
}
// To acces database we need to have an instance, so DatabaseHelper mDbHelper = new DatabaseHelper(getContext());