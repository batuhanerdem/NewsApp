package com.example.newsapp.data.repository

import com.example.newsapp.data.api.NewService
import com.example.newsapp.domain.model.APIResponse
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class NewRepositoryImpl @Inject constructor(private val service: NewService) : NewRepository {
    override suspend fun getNews(country: String, tag: String): Resource<APIResponse> {
        val response = service.getNews(country, tag)
        return convertResponseToResource(response)
    }

    private fun convertResponseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        try {
            response.body()?.let {
                return Resource.Success(it)
            }
            return Resource.Success(APIResponse(emptyList(), true))
        } catch (e: HttpException) {
            return Resource.Error(e.localizedMessage ?: "An unexpected error occured")
        } catch (e: IOException) {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }
}