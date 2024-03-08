package com.mubarak.newscastmb.data.sources.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NewsItems(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String?
):Parcelable