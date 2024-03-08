package com.mubarak.newscastmb.data.sources.remote.dto

import com.mubarak.newscastmb.data.sources.local.NewsItems

data class News(
    val articles: List<NewsItems>,
    val status: String,
    val totalResults: Int
)