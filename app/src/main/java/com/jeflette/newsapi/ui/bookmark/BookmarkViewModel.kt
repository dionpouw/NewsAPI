package com.jeflette.newsapi.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.newsapi.data.NewsRepository
import com.jeflette.newsapi.data.entity.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private var _bookmarkedNews = MutableLiveData<List<Articles>>()
    val bookmarkedNews: LiveData<List<Articles>> = _bookmarkedNews

    fun getBookmarkNews() {
        viewModelScope.launch {
            _bookmarkedNews.value = repository.getArticlesBookmarked()
        }
    }
}