package com.example.cococ.remote;

import com.example.cococ.data.RssFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("/rss/tin-moi-nhat.rss")
    Call<RssFeed> getFeed();
}
