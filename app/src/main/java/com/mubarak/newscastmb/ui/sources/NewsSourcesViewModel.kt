package com.mubarak.newscastmb.ui.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mubarak.newscastmb.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {


    val getAllNewsSources = newsRepository.getNewsSources().cachedIn(viewModelScope).catch {
        emit(PagingData.empty())
    }

}