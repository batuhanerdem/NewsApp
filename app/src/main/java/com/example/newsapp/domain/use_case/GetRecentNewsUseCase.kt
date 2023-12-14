package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.utils.DateUtils
import com.example.newsapp.utils.DateUtils.formatDateRelativeToToday
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

//@ViewModelScoped
class GetRecentNewsUseCase @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase
) {
    private var newList = mutableListOf<NewWithGenre>()
    suspend fun execute(): Flow<Resource<List<NewWithGenre>>> = flow {
        emit(Resource.Loading())
        val allNews = getAllNewsUseCase.execute()
        allNews.onEach {
            addNewsToTheListByDate(it.data)
        }.collect()
        for (new in newList) {
            println(new.new.date.formatDateRelativeToToday())
        }
        // you may want to rearrange newList
        emit(Resource.Success(newList))
    }

    private fun addNewsToTheListByDate(newWithGenreList: List<NewWithGenre>?) {
        if (newWithGenreList == null) return
        if (newList.isEmpty()) {
            val size = if (newWithGenreList.size <= 6) newWithGenreList.size else 6
            for (i in 0 until size) {
                newList.add(newWithGenreList[i])
            }
        }

        for (new in newWithGenreList) {
            val (oldestNewDate, oldestIndex) = getOldestNewDateInNewListWithIndex()
            val isMyDateOlder = DateUtils.isFirstOlder(new.new.date, oldestNewDate)
            if (isMyDateOlder == true) newList[oldestIndex] = new
        }
    }

    private fun getOldestNewDateInNewListWithIndex(): Pair<String, Int> {
        val oldestNew = newList.minBy { it.new.date }
        val oldestIndex = newList.indexOf(oldestNew)
        return oldestNew.new.date to oldestIndex
    }
}