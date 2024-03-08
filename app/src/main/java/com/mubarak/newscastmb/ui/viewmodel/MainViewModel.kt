package com.mubarak.newscastmb.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mubarak.newscastmb.data.repository.NewsRepository
import com.mubarak.newscastmb.data.sources.local.NewsItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun categoryNews(category: String): Flow<PagingData<NewsItems>> {
        return newsRepository.getCategoryNews(category).cachedIn(viewModelScope)
    }

}
