package com.mubarak.newscastmb.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mubarak.newscastmb.data.sources.local.BookmarkNews
import kotlinx.coroutines.flow.Flow

/**BookmarkNewsDao for bookmarking the articles*/
@Dao
interface BookmarkNewsDao {

    @Upsert
    suspend fun insertNews(bookmarkNews: BookmarkNews)

    @Query("SELECT * FROM BookmarkNews")
    fun getAllBookmarkedNews(): Flow<List<BookmarkNews>>

    @Query("Delete from BookmarkNews")
    suspend fun deleteAllBookmarkedNews()

    @Delete
    suspend fun deleteBookmarkedNews(bookmarkNews: BookmarkNews)


}