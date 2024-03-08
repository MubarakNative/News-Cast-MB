package com.mubarak.newscastmb.data.sources.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mubarak.newscastmb.data.sources.local.NewsItems
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Upsert
    suspend fun insertListOfNews(newsItems: List<NewsItems>)

    @Query("SELECT * FROM NewsItems")
     fun getAllNews():PagingSource<Int, NewsItems>

    @Query("DELETE FROM NewsItems")
    suspend fun clearAllNews()


}