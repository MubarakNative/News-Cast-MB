package com.mubarak.newscastmb.data.repository

import com.mubarak.newscastmb.data.sources.local.BookmarkNews
import kotlinx.coroutines.flow.Flow

/**Bookmark Repository*/
interface BookmarkRepository {
    suspend fun insertBookmarkNews(bookmarkNews: BookmarkNews)

    fun getAllBookmarkedNews(): Flow<List<BookmarkNews>>

    suspend fun deleteBookmarkedNews(bookmarkNews: BookmarkNews)
}