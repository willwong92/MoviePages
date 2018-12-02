package com.willwong.moviepages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by WillWong on 11/16/18.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    //private Integer[] images = {com.willwong.moviepages.R.drawable.puppiesimage, com.willwong.moviepages.R.drawable.sleeping_dog, com.willwong.moviepages.R.drawable.alaskan_malamute};
    ArrayList<String> imageList = new ArrayList<>();


    public ViewPagerAdapter(Context context) {
        this.mContext = context;
        //imageList = list;
        //notifyDataSetChanged();
    }
    public void setData(ArrayList<String> data) {
        imageList = data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {

        if (imageList != null) {
            return imageList.size();
        }
        return 0;
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

        Picasso.get().load(imageList.get(position))
                .placeholder(R.drawable.image_uploading).error(R.drawable.image_not_found)
                .into(imageView);

        view.setTag(position);
        ViewPager pg = (ViewPager) container;
        pg.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        int index = imageList.indexOf(object);
        if (index == -1) {
            return POSITION_NONE;
        } else {
            return index;
        }
    }
}

