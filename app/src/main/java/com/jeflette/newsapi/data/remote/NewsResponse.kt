package com.jeflette.newsapi.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.jeflette.newsapi.data.entity.Articles
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<Articles?>? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

