package com.example.newsapp.data.remote.repository

import com.example.newsapp.data.remote.service.NewService
import com.example.newsapp.domain.model.APIResponse
import com.example.newsapp.domain.model.New
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class NewRepositoryImpl @Inject constructor(private val service: NewService) : NewRepository {
    override suspend fun getNews(country: String, tag: String): Resource<APIResponse> {
        return try {
            val response = service.getNews(country, tag)
            convertResponseToResource(response)

        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        }

    }

    private fun convertResponseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        response.body()?.let {
            return Resource.Success(it)
        }
        return Resource.Success(APIResponse(emptyList(), true))

    }

    override fun newToNewWithGenre(new: New, genre: String): NewWithGenre {
        return NewWithGenre(new, genre)
    }
}