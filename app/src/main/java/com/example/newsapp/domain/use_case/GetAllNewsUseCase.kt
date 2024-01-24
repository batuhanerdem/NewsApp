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
class GetAllNewsUseCase @Inject constructor(private val newRepository: NewRepository) {
    suspend fun execute(country: Countries): Flow<Resource<List<NewWithGenre>>> =
        flow {
            emit(Resource.Loading())
            val newList = mutableListOf<NewWithGenre>()
            enumValues<Tags>().map { tag ->
                val result = newRepository.getNews(country.value, tag.value)
                if (result.data?.success != true) {
                    emit(Resource.Error(result.message))
                } else {
                    val newsWithGenres = result.data.result.map { new ->
                        newRepository.newToNewWithGenre(new, tag.title)
                    }
                    newList.addAll(newsWithGenres)
                }
            }
            emit(Resource.Success(newList))
        }
}