package com.willwong.moviepages.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

/**
 * Created by WillWong on 1/28/19.
 */

public class Movie implements Parcelable {
    private String id;
    private String overview;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name="poster_path")
    private String posterPath;
    @Json(name="backdrop_path")
    private String backDrop;
    private String title;
    @Json(name = "vote_average")
    private double reviewAverage;

    public Movie() {

    }

    private Movie (Parcel in) {
        id = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        backDrop = in.readString();
        title = in.readString();
        reviewAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        @Override
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(backDrop);
        dest.writeString(title);
        dest.writeDouble(reviewAverage);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath()
    {
        return posterPath;
    }

    public void setPosterPath(String posterPath)
    {
        this.posterPath = posterPath;
    }

    public String getBackDrop()
    {
        return backDrop;
    }

    public void setBackdropPath(String backdropPath)
    {
        this.backDrop = backdropPath;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public double getVoteAverage()
    {
        return reviewAverage;
    }

    public void setVoteAverage(double voteAverage)
    {
        this.reviewAverage = voteAverage;
    }



}
