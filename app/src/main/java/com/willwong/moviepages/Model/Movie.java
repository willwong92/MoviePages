package com.willwong.moviepages.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

/**
 * Created by WillWong on 1/28/19.
 */


@Entity(tableName = "Movies")
public class Movie implements Parcelable {
    @PrimaryKey
    private int id;
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
    //columns for distinguishing sort order
    private int popularity_id;
    private int top_rated_id;
    private int upcoming_id;
    private int nowplaying_id;

    @Ignore
    public Movie() {

    }

    public Movie(int id, String overview, String releaseDate, String posterPath, String backDrop, String title, double reviewAverage, int popularity_id, int top_rated_id,
    int upcoming_id, int nowplaying_id) {
        this.id = id;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backDrop = backDrop;
        this.title = title;
        this.reviewAverage = reviewAverage;
        this.popularity_id = popularity_id;
        this.top_rated_id = top_rated_id;
        this.upcoming_id = upcoming_id;
        this.nowplaying_id = nowplaying_id;
    }
    @Ignore
    private Movie (Parcel in) {
        id = in.readInt();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        backDrop = in.readString();
        title = in.readString();
        reviewAverage = in.readDouble();
        popularity_id = in.readInt();
        top_rated_id = in.readInt();
        upcoming_id = in.readInt();
        nowplaying_id = in.readInt();
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
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(backDrop);
        dest.writeString(title);
        dest.writeDouble(reviewAverage);
        dest.writeInt(popularity_id);
        dest.writeInt(top_rated_id);
        dest.writeInt(upcoming_id);
        dest.writeInt(nowplaying_id);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public void setBackDrop(String backDrop)
    {
        this.backDrop = backDrop;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public double getReviewAverage()
    {
        return reviewAverage;
    }

    public void setReviewAverage(double reviewAverage)
    {
        this.reviewAverage = reviewAverage;
    }

    public int getPopularity_id(){
        return popularity_id;
    }
    public void setPopularity_id(int popularity) {
        this.popularity_id = popularity_id;
    }

    public int getTop_rated_id(){
        return top_rated_id;
    }

    public void setTop_rated_id(int top_rated){
        this.top_rated_id = top_rated;
    }
    public int getUpcoming_id(){
        return upcoming_id;
    }
    public void setUpcoming_id(int upcoming) {
        this.upcoming_id = upcoming_id;
    }
    public void setNowplaying_id(int nowplaying){
        this.nowplaying_id = nowplaying_id;
    }
    public int getNowplaying_id(){
        return nowplaying_id;
    }

}
