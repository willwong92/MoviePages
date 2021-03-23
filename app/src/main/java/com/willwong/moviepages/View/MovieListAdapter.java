package com.willwong.moviepages.View;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.willwong.moviepages.R;
import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.utilities.RestApi;

import java.util.List;

/**
 * Created by WillWong on 11/14/18.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieInfoAdapterViewHolder>{
    private List<Movie> mDataset;
    private static String TAG = "MoviePages";
    private Context context;
    MovieListFragment.MovieCallBackListener movieCallBackListener;

    public class MovieInfoAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        View title;
        TextView movieName;
        FrameLayout frameLayout;
        //This constructor infaltes the row item for a viewholder and does view lookups
        // to find each subview
        public MovieInfoAdapterViewHolder(View v) {
            super(v);
            //Stores the item in a public final member variable
            movieName = (TextView) v.findViewById(R.id.movie_name);
            title = (View) v.findViewById(R.id.movie_title);
            poster = (ImageView)v.findViewById(R.id.movie_poster);
            frameLayout = v.findViewById(R.id.movie_container);

        }


    }
    //empty constructor
    public MovieListAdapter(List<Movie> mDataset, Context context, MovieListFragment.MovieCallBackListener movieCallBackListener) {
        this.movieCallBackListener = movieCallBackListener;
        this.context = context;
        this.mDataset = mDataset;

    }

    // Create new views (invoked by the layout manager)
    // and involves inflating a layout for the viewholder from XML and returns it.
    @Override
    public MovieListAdapter.MovieInfoAdapterViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(com.willwong.moviepages.R.layout.movie_item, parent, false);
        final MovieInfoAdapterViewHolder vh = new MovieInfoAdapterViewHolder(v);

        return vh;
    }

    // Binds the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MovieInfoAdapterViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (holder.poster == null) {
            holder.poster = new ImageView(context);
        }
        Picasso.get()
        .load(RestApi.getPosterPath(mDataset.get(position).getPosterPath()))
                .placeholder(R.drawable.image_uploading).error(R.drawable.image_not_found)
                .into(holder.poster);
        holder.movieName.setText(mDataset.get(position).getTitle());
        //callback listener to transmit data to activity
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieCallBackListener.getMovie(mDataset.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.size();
    }

    public void setmDataset(List<Movie> dataSet) {
        mDataset = dataSet;
        notifyDataSetChanged();
    }
}
