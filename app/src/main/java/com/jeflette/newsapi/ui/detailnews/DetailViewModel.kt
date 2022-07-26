package com.jeflette.newsapi.ui.detailnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.newsapi.data.NewsRepository
import com.jeflette.newsapi.data.entity.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    fun updateArticles(articles: Articles, newState: Boolean) {
        viewModelScope.launch {
            repository.insertArticle(articles)
            repository.setArticleBookmarked(articles, newState)
        }
    }
}