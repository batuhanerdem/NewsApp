package com.example.newsapp.data.repository

import com.example.newsapp.data.api.NewService
import com.example.newsapp.domain.model.APIResponse
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class NewRepositoryImpl @Inject constructor(private val service: NewService) : NewRepository {
    override suspend fun getNews(country: String, tag: String): Resource<APIResponse> {
        val response = service.getNews(country, tag)
        return convertResponseToResource(response)
    }

    private fun convertResponseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let { data ->
                return Resource.Success(data)
            }
        }
        return Resource.Error(response.message())
    }
}