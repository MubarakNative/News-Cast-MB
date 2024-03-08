package com.mubarak.newscastmb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mubarak.newscastmb.data.repository.NewsRepository
import com.mubarak.newscastmb.data.sources.local.NewsItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewmodel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _searchResults: MutableStateFlow<PagingData<NewsItems>> = MutableStateFlow(PagingData.empty())
    val searchResults: StateFlow<PagingData<NewsItems>> = _searchResults.asStateFlow()

    fun getNewsBySearch(searchQuery: String) {

        searchJob?.cancel()

        /**Debounce delay for search don't trigger network calls
         * for per keystroke in search fragment*/
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            newsRepository.newsQueryPager(searchQuery).catch {
                emit(PagingData.empty())
            }.cachedIn(viewModelScope).collect {
                _searchResults.value = it
            }
        }
    }

    companion object {
        private const val DEBOUNCE_DELAY = 400L
    }

}