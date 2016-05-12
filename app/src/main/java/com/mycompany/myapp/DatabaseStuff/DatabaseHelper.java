package com.mycompany.myapp.DatabaseStuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.Objects.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This classed is used for all information exchange with the application's SQLite database.
 *
 * Created by Ruben on 27-02-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
        //if you change the database schema, you must increment the database version

        /* Required constructor which calls the superclass's constructor to initialise the database helper. */
        public DatabaseHelper(Context context) {
            super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
        }

        /* Initialises the database by creating the landmark, quest, and user tables. */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBConstants.SQL_CREATE_LANDMARK_ENTRIES);
            db.execSQL(DBConstants.SQL_CREATE_QUEST_ENTRIES);
            db.execSQL(DBConstants.SQL_CREATE_USER_ENTRIES);
        }

        /* Upgrades the database to a newer version by deleting all tables and creating them anew. */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //this database is only a cache for online data, so its upgrade policy is to simply discard the data and start over
            db.execSQL(DBConstants.SQL_DELETE_lANDMARK_ENTRIES);
            db.execSQL(DBConstants.SQL_DELETE_QUEST_ENTRIES);
            db.execSQL(DBConstants.SQL_DELETE_USER_ENTRIES);
            onCreate(db);
        }

        /* Downgrades the database to an older version in the same way as it would upgrade it. */
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        /* Inserts a Landmark object into the database together with an ID. */
        private void putLandmarkInformation(DatabaseHelper helper, String landmarkID, byte[] object){
            SQLiteDatabase sq = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.LANDMARK_ID, landmarkID);
            cv.put(DBConstants.LANDMARK, object);
            sq.insert(DBConstants.TABLE_NAME_LANDMARK, null, cv);
            Log.d("COMMENT", "Tried putting landmark in database");
        }

        /* Inserts a Quest object into the database together with an ID. */
        private void putQuestInformation(DatabaseHelper helper, String questID, byte[] object){
            SQLiteDatabase sq = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.QUEST_ID, questID);
            cv.put(DBConstants.QUEST, object);
            sq.insert(DBConstants.TABLE_NAME_QUEST, null, cv);
            Log.d("COMMENT", "Tried putting quest in database");
        }

        /* Inserts a User object into the database together with an ID. */
        private void putUserInformation(DatabaseHelper helper, String userID, byte[] object){ //TODO: ID assigning should be done safely, now its not
            SQLiteDatabase sq = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.USER_ID, userID);
            cv.put(DBConstants.USER, object);
            sq.insert(DBConstants.TABLE_NAME_USER, null, cv);
            Log.d("COMMENT", "Tried putting User in database");
        }

        /* Updates the User object with the specified ID within the database. */
        private void updateUser(DatabaseHelper helper, String userID, byte[] object){
            SQLiteDatabase sq = helper.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBConstants.USER_ID, userID);
            cv.put(DBConstants.USER, object);
            sq.update(DBConstants.TABLE_NAME_USER, cv, null, null); //TODO: where clause ok here?? null is ok?
            Log.d("COMMENT", "Tried putting User in database");
        }

        /* Deletes the User object with the specified ID from the database. */
        public void deleteUser(DatabaseHelper helper, String userID){
             SQLiteDatabase sq = helper.getWritableDatabase();
             sq.delete(DBConstants.TABLE_NAME_USER, DBConstants.USER_ID + "=" + userID, null);
             sq.close();
        }

        /* Deletes the Landmark object with the specified ID from the database. */
        public void deleteLandmark(DatabaseHelper helper, String landmarkID){
            SQLiteDatabase sq = helper.getWritableDatabase();
            sq.delete(DBConstants.TABLE_NAME_LANDMARK, DBConstants.LANDMARK_ID + "=" + landmarkID, null);
            sq.close();
         }

        /* Deletes the Quest object with the specified ID from the database. */
        public void deleteQuest(DatabaseHelper helper, String questID){
            SQLiteDatabase sq = helper.getWritableDatabase();
            sq.delete(DBConstants.TABLE_NAME_QUEST, DBConstants.QUEST_ID + "=" + questID, null);
            sq.close();
        }

    /* Gets all Quest objects from the database. */
    public ArrayList<Quest> getAllQuests(SQLiteDatabase db) {
        ArrayList<Quest> list = new ArrayList<>();
        //Select All query
        String selectQuery = "SELECT  * FROM " + DBConstants.TABLE_NAME_QUEST;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to a list
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

    /* Gets all Landmark objects from the database. */
    public ArrayList<Landmark> getAllLandmarks(SQLiteDatabase db) {
        ArrayList<Landmark> list = new ArrayList<>();
        //Select All query
        String selectQuery = "SELECT  * FROM " + DBConstants.TABLE_NAME_LANDMARK;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to a list
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


    /* Gets a User object from the database, NOTE: this will close the database after use, so it can't be used twice. */
    public User getUser(SQLiteDatabase db) {
        //Select All query
        String selectQuery = "SELECT  * FROM " + DBConstants.TABLE_NAME_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //in this case there is only one user, so no looping is needed
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


    /* Converts a Landmark object into a byte array and writes it to the database. */
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

    /* Converts a Quest object into a byte array and writes it to the database. */
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

    /* Converts a User object into a byte array and writes it to the database. */
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

    /* Converts a User object into a byte array and writes it to the database. */
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

//to access the database we need to have an instance, so use DatabaseHelper mDbHelper = new DatabaseHelper(getContext());