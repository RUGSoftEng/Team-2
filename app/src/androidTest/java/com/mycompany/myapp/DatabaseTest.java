package com.mycompany.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

/**
 * Created by Ruben on 31/03/2016.
 */
public class DatabaseTest extends AndroidTestCase {
        private DatabaseHelper helper;
        private static final int UNUSED_ID = 9999; //not used id in database
        private  Context mMockContext;

        @Override
        public void setUp() throws Exception {
            super.setUp();
            mMockContext = getContext();
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

    }
