package com.willwong.moviepages;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URL;

import com.willwong.moviepages.utilities.MovieJsonUtilities;
import com.willwong.moviepages.utilities.NetworkUtilities;

/**
 * Created by WillWong on 11/15/18.
 */

public class RecyclerViewFragment extends Fragment {
    private static final String TAG = RecyclerViewFragment.class.getSimpleName();
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected RecyclerView mRecyclerView;
    protected movieInfoAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;
    private String args;

    //Empty constructor for modularity and fragment reuse
    public RecyclerViewFragment() {
        // Required empty public constructor
    }
    // Static method for creating a fragment with arguements because a fragment must have only
    // a constructor with no arguments.
    public static RecyclerViewFragment newInstance(String input) {
        RecyclerViewFragment frag = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString("Input", input);
        frag.setArguments(args);
        return frag;
    }
    // This method is called when the Fragment instance is being created, or re-created.
    //Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retrieve arguements from newInstance

    }
    //Called when Fragment shuold create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(com.willwong.moviepages.R.layout.fragment_recycler_view, container, false);
        rootView.setTag(TAG);
        args = getArguments().getString("Input");

        return rootView;
    }
    //This event is triggered soon after onCreateView().
    //onCreatedView() is only called if the view returned from onCreateView is non-null.
    //Any view setup should occur here. E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // BEGIN_INCLUDE(intializeRecyclerView)
        mRecyclerView = (RecyclerView) view.findViewById(com.willwong.moviepages.R.id.my_recycler_view);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new movieInfoAdapter();
        //Set movieInfoAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        new fetchMovieDataTask().execute(args);

    }
    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.
    @Override
    public void onDetach() {
        super.onDetach();

    }
    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class fetchMovieDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            if (params.length == 0) {
                Log.i(TAG, "No input");
                return null;
            }

            String movieInput = params[0];
            URL movieRequestUrl = NetworkUtilities.buildSearchUrl(movieInput);

            try {
                String movieResponse = NetworkUtilities.httpUrlResponse(movieRequestUrl);

                mDataset = MovieJsonUtilities.getMovieStringsFromJson(getActivity(), movieResponse);

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void v) {
            mAdapter.setmDataset(mDataset);
            mAdapter.notifyDataSetChanged();
        }
    }

}
