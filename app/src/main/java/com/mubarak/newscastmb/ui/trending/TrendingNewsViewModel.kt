package com.mubarak.newscastmb.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mubarak.newscastmb.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class TrendingNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
):ViewModel() {

    val getAllNews = newsRepository.trendingNewsPagedData.cachedIn(viewModelScope).catch {
        emit(PagingData.empty())
    }

}

