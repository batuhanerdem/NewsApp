package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.New
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class GetNewsUseCase @Inject constructor(private val newRepository: NewRepository) {

    suspend fun execute(country: String, tag: String): Flow<Resource<List<New>>> = flow {
        emit(Resource.Loading())
        val result = newRepository.getNews(country, tag)
        if (result.data?.success != true) {
            emit(Resource.Error(result.message))
        } else {
            emit(Resource.Success(result.data.result))
        }
    }
}