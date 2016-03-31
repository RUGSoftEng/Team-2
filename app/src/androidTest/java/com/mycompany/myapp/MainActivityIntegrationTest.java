package com.mycompany.myapp;

import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Lutz on 29-3-2016.
 */
public class MainActivityIntegrationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityIntegrationTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testpickbutton() {
        Button bt1=(Button)getActivity().findViewById(R.id.button_main);
        assertNotNull(bt1);
    }

    @SmallTest
    public void testmapsbutton() {
        Button bt2=(Button)getActivity().findViewById(R.id.mapButton);
        assertNotNull(bt2);
    }

    @SmallTest
    public void testcontinuebutton() {
        Button bt3=(Button)getActivity().findViewById(R.id.continueButton);
        assertNull(bt3);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
