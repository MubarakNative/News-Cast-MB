package com.mubarak.newscastmb.data.sources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsRemoteKey (
    @PrimaryKey
    val id:String,
    val nextKey:Int?,
    val prevKey:Int?,
)