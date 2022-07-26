package com.jeflette.newsapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeflette.newsapi.data.entity.Articles
import com.jeflette.newsapi.util.Converter

@Database(entities = [Articles::class], version = 1)
@TypeConverters(Converter::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}