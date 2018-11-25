package com.willwong.moviepages;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by WillWong on 11/16/18.
 */

public class ViewPagerAdapter extends PagerAdapter{
    private Context mContext;
    private LayoutInflater layoutInflater;
    private Integer[] images = {com.willwong.moviepages.R.drawable.puppiesimage, com.willwong.moviepages.R.drawable.sleeping_dog, com.willwong.moviepages.R.drawable.alaskan_malamute};

    public ViewPagerAdapter(Context context) {
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(com.willwong.moviepages.R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        ViewPager pg = (ViewPager) container;
        pg.addView(view, 0);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

}

