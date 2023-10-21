package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.APIResponse
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Resource
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newRepository: NewRepository) {
    suspend fun execute(country: String, tag: String): Resource<APIResponse> {
        return newRepository.getNews(country, tag)
    }
}