package com.mubarak.newscastmb.data.sources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkNews(

    val title: String,
    @PrimaryKey
    val url: String,
    val author: String?,
    val imageUrl:String?,
    val publishedAt: String?
)
