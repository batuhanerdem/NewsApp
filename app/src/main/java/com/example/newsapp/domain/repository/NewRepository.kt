package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.APIResponse
import com.example.newsapp.utils.Resource

interface NewRepository {
    suspend fun getNews(country: String, tag: String): Resource<APIResponse>
}