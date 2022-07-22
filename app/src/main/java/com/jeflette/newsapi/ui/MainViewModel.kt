package com.jeflette.newsapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.newsapi.api.ApiService
import com.jeflette.newsapi.data.remote.response.NewsResponse
import com.jeflette.newsapi.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: ApiService) : ViewModel() {
    private val newsLiveData = MutableLiveData<NewsResponse?>()
    val news: LiveData<NewsResponse?> = newsLiveData

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingLiveData


    fun getNews() {
        viewModelScope.launch {
            isLoadingLiveData.value = true
            api.getNews("us", Constants.apiKey).also {
                isLoadingLiveData.value = false
                newsLiveData.value = it
            }
        }
    }
}