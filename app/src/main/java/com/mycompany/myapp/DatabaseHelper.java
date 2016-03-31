package com.mycompany.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
            db.execSQL(DBConstants.SQL_CREATE_USER_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(DBConstants.SQL_DELETE_lANDMARK_ENTRIES);
            db.execSQL(DBConstants.SQL_DELETE_QUEST_ENTRIES);
            db.execSQL(DBConstants.SQL_DELETE_USER_ENTRIES);
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

        public void putUserInformation(DatabaseHelper helper, int userID, byte[] object){ //TODO: ID assigning should be done safely, now its not
            SQLiteDatabase sq = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.USER_ID, userID);
            cv.put(DBConstants.USER, object);
            sq.insert(DBConstants.TABLE_NAME_USER, null, cv);
            Log.d("COMMENT", "Tried putting User in database");
        }

        private void updateUser(DatabaseHelper helper, int userID, byte[] object){
            SQLiteDatabase sq = helper.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.USER_ID, userID);
            cv.put(DBConstants.USER, object);
            sq.update(DBConstants.TABLE_NAME_USER, cv, null, null); //TODO: where clause ok here?? null is ok?
            Log.d("COMMENT", "Tried putting User in database");
        }


        public void deleteUser(DatabaseHelper helper, int userID){
             SQLiteDatabase sq = helper.getWritableDatabase();
             sq.delete(DBConstants.TABLE_NAME_USER, DBConstants.USER_ID + "=" + userID, null);
             sq.close();
        }

        public void deleteLandmark(DatabaseHelper helper, int landmarkID){
            SQLiteDatabase sq = helper.getWritableDatabase();
            sq.delete(DBConstants.TABLE_NAME_LANDMARK, DBConstants.LANDMARK_ID + "=" + landmarkID, null);
            sq.close();
         }

        public void deleteQuest(DatabaseHelper helper, int questID){
            SQLiteDatabase sq = helper.getWritableDatabase();
            sq.delete(DBConstants.TABLE_NAME_QUEST, DBConstants.QUEST_ID + "=" + questID, null);
            sq.close();
        }

    //gets all quest from db
    public ArrayList<Quest> getAllQuests(SQLiteDatabase db) {
        ArrayList<Quest> list = new ArrayList<Quest>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBConstants.TABLE_NAME_QUEST;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ByteArrayInputStream bis = new ByteArrayInputStream(cursor.getBlob(1));
                ObjectInput in = null;

                try {
                    in = new ObjectInputStream(bis);
                    Quest quest = (Quest) in.readObject();
                    list.add(quest);

                    in.close();
                    bis.close();
                } catch (IOException e) {
                    Log.e("IOException", "failed to create input stream for landmark");
                } catch (ClassNotFoundException ex){
                    Log.e("ClassNotFound", "failed to find class while creating landmark");
                }

            } while (cursor.moveToNext());
        }
        return list;
    }

    //Gets all Landmarks from db
    public ArrayList<Landmark> getAllLandmarks(SQLiteDatabase db) {
        ArrayList<Landmark> list = new ArrayList<Landmark>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBConstants.TABLE_NAME_LANDMARK;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ByteArrayInputStream bis = new ByteArrayInputStream(cursor.getBlob(1));
                ObjectInput in = null;

                try {
                    in = new ObjectInputStream(bis);
                    Landmark landmark = (Landmark) in.readObject();
                    list.add(landmark);

                    in.close();
                    bis.close();
                } catch (IOException e) {
                    Log.e("IOException", "failed to create input stream for landmark");
                } catch (ClassNotFoundException ex){
                    Log.e("ClassNotFound", "failed to find class while creating landmark");
                }

            } while (cursor.moveToNext());
        }
        return list;
    }


    //Gets user from db, NOTE: will close database after use, so cant be used twice
    public User getUser(SQLiteDatabase db) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBConstants.TABLE_NAME_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // In this case there is only one user, so no looping
        cursor.moveToFirst();
                ByteArrayInputStream bis = new ByteArrayInputStream(cursor.getBlob(1));
                ObjectInput in = null;
        User user = null;
                try {
                    in = new ObjectInputStream(bis);
                    user = (User) in.readObject();

                    in.close();
                    bis.close();
                } catch (IOException e) {
                    Log.e("IOException", "failed to create input stream for User");
                } catch (ClassNotFoundException ex){
                    Log.e("ClassNotFound", "failed to find class while creating User");
                }
        db.close();
        return user;
    }


    //convert to byteArray and write into database
    public void putInDatabase(DatabaseHelper db, Landmark l) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);

            out.writeObject(l);
            byte[] data = bos.toByteArray();
            db.putLandmarkInformation(db, l.getID(), data);

            out.close();
            bos.close();
        } catch (IOException ex) {
            // TODO: catch error
            Log.e("IOException", "Something went wrong with creating database", ex);
        }
    }

    public void putInDatabase(DatabaseHelper db, Quest q) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);

            out.writeObject(q);
            byte[] data = bos.toByteArray();
            db.putQuestInformation(db, q.getID(), data);

            out.close();
            bos.close();
        } catch (IOException ex) {
            // TODO: catch error
            Log.e("IOException", "Something went wrong with creating database", ex);
        }
    }


    public void updateInDatabase(DatabaseHelper db, User u){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);

            out.writeObject(u);
            byte[] data = bos.toByteArray();
            db.updateUser(db, u.getID(), data);

            out.close();
            bos.close();
        } catch (IOException ex) {
            // TODO: catch error
            Log.e("IOException", "Something went wrong with creating database", ex);
        }
    }

    public void putInDatabase(DatabaseHelper db, User u) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);

            out.writeObject(u);
            byte[] data = bos.toByteArray();
            db.putUserInformation(db, u.getID(), data);

            out.close();
            bos.close();
        } catch (IOException ex) {
            // TODO: catch error
            Log.e("IOException", "Something went wrong with creating database", ex);
        }
    }
}
// To acces database we need to have an instance, so DatabaseHelper mDbHelper = new DatabaseHelper(getContext());