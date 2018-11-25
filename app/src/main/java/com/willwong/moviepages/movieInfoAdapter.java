package com.willwong.moviepages;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by WillWong on 11/14/18.
 */

public class movieInfoAdapter extends RecyclerView.Adapter<movieInfoAdapter.movieInfoAdapterViewHolder>{
    protected String[] mDataset;
    private static String TAG = "MoviePages";

    public static class movieInfoAdapterViewHolder extends RecyclerView.ViewHolder {
        //for now each item is a string
        public TextView textView;
        //This constructor infaltes the row item for a viewholder and does view lookups
        // to find each subview
        public movieInfoAdapterViewHolder(TextView v) {
            super(v);
            //Stores the item in a public final member variable
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(com.willwong.moviepages.R.id.textview);
        }
        /*public TextView getTextView() {
            return textView;
        }*/
    }
    //empty constructor
    public movieInfoAdapter() {

    }

    // Create new views (invoked by the layout manager)
    // and involves inflating a layout for the viewholder from XML and returns it.
    @Override
    public movieInfoAdapter.movieInfoAdapterViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(com.willwong.moviepages.R.layout.text_view_row, parent, false);
        //TextView tv = (TextView)v.findViewById(R.id.textview);
        movieInfoAdapterViewHolder vh = new movieInfoAdapterViewHolder((TextView)v);
        return vh;
    }

    // Binds the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(movieInfoAdapterViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.length;
    }

    public void setmDataset(String[] dataSet) {
        mDataset = dataSet;
        notifyDataSetChanged();
    }
}
