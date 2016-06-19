package rugse.team2.MeetGroningen.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import rugse.team2.MeetGroningen.R;

/**
 * This class represents the activity (Android window) for displaying the tutorial,
 * which utilises a swipe adapter instance, and can be accessed from the main screen.
 */
public class TutorialActivity extends AppCompatActivity {
    /** the view object used to move back and forth between pages of data  */
    ViewPager viewPager;
    /** the adapter object used by the view object to generate the pages as images to be swiped back and forth */
    CustomSwipeAdapter adapter;

    /** Initialises the activity as described above, creating the adapter and assigning it to the view. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tutorial);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
    }
}