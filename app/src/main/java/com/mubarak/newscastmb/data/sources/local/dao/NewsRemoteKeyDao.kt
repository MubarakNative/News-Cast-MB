package com.mubarak.newscastmb.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mubarak.newscastmb.data.sources.local.NewsRemoteKey

@Dao
interface NewsRemoteKeyDao {

    @Upsert
    suspend fun insertNewsRemoteKey(newsRemoteKey: List<NewsRemoteKey>)

    @Query("SELECT * FROM NewsRemoteKey WHERE id =:url")
    suspend fun getNewsRemoteKeyById(url: String): NewsRemoteKey

    @Query("DELETE FROM NewsRemoteKey")
    suspend fun clearAllNewsRemoteKeys()
}