package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.utils.DateUtils.isNewerThan
import com.example.newsapp.utils.DateUtils.toDate
import com.example.newsapp.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class GetRecentNewsUseCase @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase
) {
    private var newList = mutableListOf<NewWithGenre>()
    suspend fun execute(country: Countries): Flow<Resource<List<NewWithGenre>>> = flow {
        newList.clear()
        emit(Resource.Loading())
        val allNews = getAllNewsUseCase.execute(country)
        allNews.collect {
            addNewsToListByDate(it.data)
        }
        emit(Resource.Success(newList))
    }

    private fun addNewsToListByDate(newWithGenreList: List<NewWithGenre>?) {
        if (newWithGenreList == null) return
        if (newList.isEmpty()) {
            val size = if (newWithGenreList.size <= 8) newWithGenreList.size else 8
            for (i in 0 until size) {
                newList.add(newWithGenreList[i])
            }
        }

        for (new in newWithGenreList) {
            val (newestNewDate, newestIndex) = getNewestNewDateInNewListWithIndex()
            if (new.new.date.isNewerThan(newestNewDate) == true) newList[newestIndex] = new
        }
    }

    private fun getNewestNewDateInNewListWithIndex(): Pair<String, Int> {
        val newestNew = newList.maxBy { it.new.date.toDate().time }
        val newestIndex = newList.indexOf(newestNew)
        return newestNew.new.date to newestIndex
    }
}