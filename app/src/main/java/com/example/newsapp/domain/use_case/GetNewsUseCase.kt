package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class GetNewsUseCase @Inject constructor(private val newRepository: NewRepository) {

    suspend fun execute(country: Countries, tag: Tags): Flow<Resource<List<NewWithGenre>>> = flow {
        emit(Resource.Loading())
        val result = newRepository.getNews(country.value, tag.value)
        if (result.data?.success != true) {
            emit(Resource.Error(result.message))
        } else {
            val newsWithGenres = result.data.result.map {
                newRepository.newToNewWithGenre(it, tag.title)
            }
            emit(Resource.Success(newsWithGenres))
        }
    }
}