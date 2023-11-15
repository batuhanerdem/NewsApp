package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.New
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.domain.repository.NewRepository
import com.example.newsapp.utils.Country
import com.example.newsapp.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class GetAllNewsUseCase @Inject constructor(private val newRepository: NewRepository) {
    suspend fun execute(): Flow<Resource<List<New>>> = flow {
        emit(Resource.Loading())
        val newList = mutableListOf<New>()
        enumValues<Tags>().map {
            val result = newRepository.getNews(Country.selectedCountry.value, it.value)
            if (result.data?.success != true) {
                emit(Resource.Error(result.message))
            } else {
                newList.addAll(result.data.result)
                emit(Resource.Success(newList))
            }
        }
    }
}