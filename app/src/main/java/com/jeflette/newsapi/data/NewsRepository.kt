package com.jeflette.newsapi.data

import androidx.room.withTransaction
import com.jeflette.newsapi.api.ApiService
import com.jeflette.newsapi.data.entity.Articles
import com.jeflette.newsapi.data.local.ArticlesDatabase
import com.jeflette.newsapi.util.Constants
import com.jeflette.newsapi.util.networkBoundResource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    val api: ApiService,
    private val db: ArticlesDatabase
) {
    private val articlesDao = db.articlesDao()

    fun getArticles() = networkBoundResource(
        query = {
            articlesDao.getArticles()
        },
        fetch = {
            api.getNews("us", Constants.apiKey)
        },
        saveFetchResult = { newsResponse ->
            db.withTransaction {
                db.articlesDao().insertArticles(newsResponse?.articles as List<Articles>)
            }
        }
    )

    suspend fun getArticlesBookmarked() = db.articlesDao().getArticlesBookmarked()

    suspend fun insertArticle(article: Articles) = db.articlesDao().insertArticle(article)

    suspend fun setArticleBookmarked(article: Articles, isBookmarked: Boolean) {
        article.isBookmarked = isBookmarked
        db.articlesDao().updateArticle(article)
    }
}