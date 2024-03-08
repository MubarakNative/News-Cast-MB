package com.mubarak.newscastmb.data.sources.remote.paging.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mubarak.newscastmb.data.sources.remote.NewsApi
import com.mubarak.newscastmb.data.sources.local.NewsItems
import com.mubarak.newscastmb.utils.AppConstants.NEWS_COUNTRY
import javax.inject.Inject

/**Paging Source for different news categories*/
class NewsPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val category:String
) : PagingSource<Int, NewsItems>() {
    override fun getRefreshKey(state: PagingState<Int, NewsItems>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.minus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItems> {
        return try {

            val currentPage = params.key ?: 1
            val response = newsApi.getTopCategoriesHeadlines(
                currentPage,
                category,
                NEWS_COUNTRY
            )

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (currentPage == response.totalResults) null else currentPage + 1

            LoadResult.Page(
                data = response.articles,
                prevKey = prevPage,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}