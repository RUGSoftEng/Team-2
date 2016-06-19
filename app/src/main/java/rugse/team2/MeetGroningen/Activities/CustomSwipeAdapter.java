package rugse.team2.MeetGroningen.Activities;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import rugse.team2.MeetGroningen.R;

/**
 * This class represents a swipe adapter, which is a simple screen lay-out for swiping
 * back and forth between a set of images, in this case the three tutorial images.
 *
 * Created by Ana on 05-21-2016.
 */
public class CustomSwipeAdapter extends PagerAdapter {
    /** the set of images */
    private int[] images = {R.mipmap.tutorial_page1, R.mipmap.tutorial_page2, R.mipmap.tutorial_page3};
    /** the application context */
    private Context context;
    /** they lay-out inflater which is received from the context and is able to inflate XML files to actual lay-outs */
    private LayoutInflater layoutInflater;

    /** Constructor which stores the given context. */
    public CustomSwipeAdapter(Context context){
        this.context = context;
    }

    /** Returns the amount of images in the swipe adapter. */
    @Override
    public int getCount() {
        return images.length;
    }

    /** Checks if the given view currently contains the given object. */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    /** Adds the images in the set to a view after inflating a swipe lay-out file into a lay-out
      * containing that view, and adds the lay-out to the given view group before returning it. */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.tutorial_image_view);

        imageView.setImageResource(images[position]);
        container.addView(item_view);

        return item_view;
    }

    /** Removes a given object from a given view group. */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((LinearLayout) object);
    }
}