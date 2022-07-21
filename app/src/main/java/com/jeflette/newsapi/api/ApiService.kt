package com.jeflette.newsapi.api

import com.jeflette.newsapi.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") query: String ,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}