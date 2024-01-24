package com.example.newsapp.data.remote.service

import com.example.newsapp.BuildConfig
import com.example.newsapp.domain.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewService {

    @Headers(ServiceConstants.CONTENT_TYPE, ServiceConstants.AUTHORIZATION)
    @GET(ServiceConstants.ENDPOINT_GET_NEWS)
    suspend fun getNews(
        @Query(ServiceConstants.QUERY_COUNTRY) country: String,
        @Query(ServiceConstants.QUERY_TAG) tag: String,
    ): Response<APIResponse>

    private object ServiceConstants {
        const val CONTENT_TYPE = "content-type: application/json"
        const val AUTHORIZATION = "authorization: ${BuildConfig.API_KEY}"
        const val ENDPOINT_GET_NEWS = "news/getNews"
        const val QUERY_COUNTRY = "country"
        const val QUERY_TAG = "tag"
    }

}




