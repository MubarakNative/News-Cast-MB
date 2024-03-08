package com.mubarak.newscastmb.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mubarak.newscastmb.data.sources.local.dao.BookmarkNewsDao
import com.mubarak.newscastmb.data.sources.local.dao.NewsDao
import com.mubarak.newscastmb.data.sources.local.dao.NewsRemoteKeyDao

@Database(
    entities = [NewsItems::class, NewsRemoteKey::class,
        BookmarkNews::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {

    abstract val getNewsDao: NewsDao
    abstract val getNewsRemoteKeyDao: NewsRemoteKeyDao
    abstract val getBookmarkedNewsDao: BookmarkNewsDao
}