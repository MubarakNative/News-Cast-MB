package com.mubarak.newscastmb.data.sources.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mubarak.newscastmb.data.sources.local.NewsDatabase
import com.mubarak.newscastmb.data.sources.local.NewsRemoteKey
import com.mubarak.newscastmb.data.sources.remote.NewsApi
import com.mubarak.newscastmb.data.sources.local.NewsItems
import com.mubarak.newscastmb.utils.AppConstants.NEWS_COUNTRY

/** Paging 3 remote mediator for Trending news fragment*/
@OptIn(ExperimentalPagingApi::class)
class TrendingNewsRemoteMediator(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
): RemoteMediator<Int, NewsItems>() {

    private val getNewsDao = newsDatabase.getNewsDao
    private val getNewsRemoteKeyDao = newsDatabase.getNewsRemoteKeyDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsItems>
    ): MediatorResult {

       return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->{
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                        val lastItem = getRemoteKeyForNextItem(state)
                    val nextKey = lastItem?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = lastItem != null
                    )
                    nextKey
                }
            }

            val response = newsApi.getGeneralNews(currentPage,NEWS_COUNTRY)

            val endOfPagination = currentPage == response.totalResults

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagination) null else currentPage + 1

            newsDatabase.withTransaction { // queries must be atomicity

                if (loadType == LoadType.REFRESH){
                    getNewsDao.clearAllNews()
                    getNewsRemoteKeyDao.clearAllNewsRemoteKeys()
                }

                val remoteKey = response.articles.map {
                    NewsRemoteKey(
                        id =it.url,
                        nextKey = nextPage,
                        prevKey = prevPage
                        )
                }

                getNewsDao.insertListOfNews(response.articles)
                getNewsRemoteKeyDao.insertNewsRemoteKey(remoteKey)
            }

           MediatorResult.Success(endOfPagination)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
    private suspend fun getRemoteKeyForNextItem(
        state: PagingState<Int, NewsItems>
    ): NewsRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            getNewsRemoteKeyDao.getNewsRemoteKeyById(it.url)
        }
    }
}
