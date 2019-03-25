package com.willwong.moviepages.Model;

import com.squareup.moshi.Json;

import java.util.List;



public class VideosResponse {

    @Json(name = "results")
    private List<Video> videos;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

}
