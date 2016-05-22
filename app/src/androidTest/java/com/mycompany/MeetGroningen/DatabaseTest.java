package com.mycompany.MeetGroningen;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.mycompany.MeetGroningen.DatabaseStuff.DatabaseHelper;
import com.mycompany.MeetGroningen.Objects.Landmark;
import com.mycompany.MeetGroningen.Objects.User;

import java.util.ArrayList;

/**
 * Created by Ruben on 31/03/2016.
 */
public class DatabaseTest extends AndroidTestCase{
        private DatabaseHelper helper;
        private static final String UNUSED_ID = "9999"; //not used id in database

    @Override
        public void setUp() throws Exception {
            super.setUp();
        Context mMockContext = new RenamingDelegatingContext(getContext(), "test_");
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
            String answerID = u.getID();
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
                if(l.get(i).getID().equals(UNUSED_ID)) {
                    answerName = l.get(i).getName();
                }
            }
            helper.deleteLandmark(helper, UNUSED_ID);
            assertEquals("TestName", answerName);
        }

    }
