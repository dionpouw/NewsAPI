package com.jeflette.newsapi.api

import com.jeflette.newsapi.data.remote.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") query: String ,
        @Query("apiKey") apiKey: String
    ): NewsResponse?

    @GET("everything")
    suspend fun getNewsSearch(
        @Query("q") query: String ,
        @Query("apiKey") apiKey: String
    ): NewsResponse?
}