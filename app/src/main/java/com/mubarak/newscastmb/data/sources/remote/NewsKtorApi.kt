package com.mubarak.newscastmb.data.sources.remote

import com.mubarak.newscastmb.data.sources.remote.dto.News
import com.mubarak.newscastmb.data.sources.remote.dto.Source
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsKtorApi {

    // general news based on country for (Trending news Fragment)
    @GET("v2/top-headlines")
    suspend fun getGeneralNews(
        @Query("page") page:Int,
        @Query("country") country:String,
    ): News

    // for searching news for (searching News)
    @GET("/v2/everything")
    suspend fun getNewsByQuery(
        @Query("page") page: Int,
        @Query("q") queryString:String,
        @Query("language") language:String="en"
    ): News

    // getting breaking news according to the category for headline news fragment
    @GET("v2/top-headlines")
    suspend fun getTopCategoriesHeadlines(
        @Query("page") page:Int,
        @Query("category") category:String,
        @Query("country") country:String,
    ): News

    // for displaying difference news sources in Sources Fragment
    @GET("v2/top-headlines/sources")
    suspend fun getNewsSources(
        @Query("language") language:String
    ): Source

}