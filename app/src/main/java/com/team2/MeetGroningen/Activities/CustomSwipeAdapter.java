package com.team2.MeetGroningen.Activities;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.team2.MeetGroningen.R;

/**
 * Created by Ana1 on 5/21/2016.
 */
public class CustomSwipeAdapter extends PagerAdapter{

    private int[] images = {R.drawable.tutorial_page1,
                            R.drawable.tutorial_page2,
                            R.drawable.tutorial_page3};

    private Context context;
    private LayoutInflater layoutInflater;



    public CustomSwipeAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.tutorial_image_view);

        imageView.setImageResource(images[position]);
        container.addView(item_view);


        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((LinearLayout)object);
    }
}
