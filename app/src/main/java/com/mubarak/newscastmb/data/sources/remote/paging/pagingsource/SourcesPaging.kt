package com.mubarak.newscastmb.data.sources.remote.paging.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mubarak.newscastmb.data.sources.remote.NewsApi
import com.mubarak.newscastmb.data.sources.remote.dto.SourceItem
import com.mubarak.newscastmb.utils.AppConstants.NEWS_LANGUAGE
import javax.inject.Inject

/** Paging Source class for sources fragment*/
class SourcesPaging @Inject constructor(private val newsApi: NewsApi) : PagingSource<Int, SourceItem>() {
    override fun getRefreshKey(state: PagingState<Int, SourceItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.minus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.plus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SourceItem> {
        return try {

            val currentPage = params.key ?: 1
            val response = newsApi.getNewsSources(NEWS_LANGUAGE)

            val endOfPage = response.sources.lastIndex
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (currentPage == endOfPage) null else currentPage + 1

            LoadResult.Page(
                data = response.sources,
                prevKey = prevPage,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}