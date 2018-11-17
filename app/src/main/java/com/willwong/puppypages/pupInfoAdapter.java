package com.willwong.puppypages;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by WillWong on 11/14/18.
 */

public class pupInfoAdapter extends RecyclerView.Adapter<pupInfoAdapter.pupInfoAdapterViewHolder> {
    protected String[] mDataset;
    private static String TAG = "PuppyPages";

    public static class pupInfoAdapterViewHolder extends RecyclerView.ViewHolder {
        //for now each item is a string
        public TextView textView;
        public pupInfoAdapterViewHolder(TextView v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.textview);
        }
        /*public TextView getTextView() {
            return textView;
        }*/
    }
    public pupInfoAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public pupInfoAdapter.pupInfoAdapterViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view_row, parent, false);
        //TextView tv = (TextView)v.findViewById(R.id.textview);
        pupInfoAdapterViewHolder vh = new pupInfoAdapterViewHolder((TextView)v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(pupInfoAdapterViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
