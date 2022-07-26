package com.jeflette.newsapi.ui.llistnews

import androidx.lifecycle.*
import com.jeflette.newsapi.data.NewsRepository
import com.jeflette.newsapi.data.remote.NewsResponse
import com.jeflette.newsapi.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val newsResponse = repository.getArticles().asLiveData()

    private val newsLiveData = MutableLiveData<NewsResponse?>()
    val news: LiveData<NewsResponse?> = newsLiveData

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingLiveData

    fun getSearchNews(query: String) {
        viewModelScope.launch {
            isLoadingLiveData.value = true
            repository.api.getNewsSearch(query, Constants.apiKey).also {
                isLoadingLiveData.value = false
                newsLiveData.value = it
            }
        }
    }
}