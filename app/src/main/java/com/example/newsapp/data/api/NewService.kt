package com.example.newsapp.data.api

import com.example.newsapp.BuildConfig
import com.example.newsapp.domain.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewService {

    @Headers("content-type: application/json", "authorization: ${BuildConfig.API_KEY}")
    @GET("news/getNews")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("tag") tag: String
    ): Response<APIResponse>
}