package com.mubarak.newscastmb.di

import com.mubarak.newscastmb.data.sources.remote.CustomInterceptor
import com.mubarak.newscastmb.data.sources.remote.NewsApi
import com.mubarak.newscastmb.utils.AppConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(CustomInterceptor().authInterceptor())
            .build()
            .create(NewsApi::class.java)
    }
}