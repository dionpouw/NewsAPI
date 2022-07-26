package com.jeflette.newsapi.di

import android.app.Application
import androidx.room.Room
import com.jeflette.newsapi.BuildConfig
import com.jeflette.newsapi.api.ApiService
import com.jeflette.newsapi.data.local.ArticlesDatabase
import com.jeflette.newsapi.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(
            GsonConverterFactory.create()
        ).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): ArticlesDatabase =
        Room.databaseBuilder(app, ArticlesDatabase::class.java, "Articles").build()
}