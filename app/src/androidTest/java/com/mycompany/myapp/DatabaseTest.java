package com.mycompany.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ruben on 31/03/2016.
 */
public class DatabaseTest extends AndroidTestCase{
        private DatabaseHelper helper;
        private static final int UNUSED_ID = 9999; //not used id in database
        private  Context mMockContext;

        @Override
        public void setUp() throws Exception {
            super.setUp();
            mMockContext =  new RenamingDelegatingContext(getContext(), "test_");
            helper = new DatabaseHelper(mMockContext);
        }

        @Override
        public void tearDown() throws Exception {
            helper.close();
            super.tearDown();
        }

        @SmallTest
        public void testAddUser(){
            User u1 = new User(UNUSED_ID);
            helper.putInDatabase(helper, u1);
            User u = helper.getUser(helper.getReadableDatabase());
            int answerID = u.getID();
            helper.deleteUser(helper, UNUSED_ID);
            assertEquals(UNUSED_ID, answerID);
        }

        @SmallTest
        public void testAddLandmark(){
            Landmark l1 = new Landmark("TestName", UNUSED_ID);
            helper.putInDatabase(helper, l1);
            ArrayList<Landmark> l = helper.getAllLandmarks(helper.getReadableDatabase());
            String answerName = null;
            for(int i = 0; i<l.size(); i++) {
                if(l.get(i).getID() == UNUSED_ID) {
                    answerName = l.get(i).getName();
                }
            }
            helper.deleteLandmark(helper, UNUSED_ID);
            assertEquals("TestName", answerName);
        }

    }
