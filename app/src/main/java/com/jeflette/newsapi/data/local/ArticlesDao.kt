package com.jeflette.newsapi.data.local

import androidx.room.*
import com.jeflette.newsapi.data.entity.Articles
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<Articles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articles: Articles)

    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<Articles>>

    @Query("SELECT * FROM articles where isBookmarked = 1")
    suspend fun getArticlesBookmarked(): List<Articles>

    @Delete
    suspend fun deleteArticle(articles: Articles)

    @Update
    suspend fun updateArticle(article: Articles)

}