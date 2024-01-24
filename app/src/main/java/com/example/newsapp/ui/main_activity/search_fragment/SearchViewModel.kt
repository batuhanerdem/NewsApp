package com.example.newsapp.ui.main_activity.search_fragment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.use_case.GetAllNewsUseCase
import com.example.newsapp.ui.base.BaseViewModel
import com.example.newsapp.utils.CountryUtils
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getAllNewsUseCase: GetAllNewsUseCase) :
    BaseViewModel<SearchActionBus>() {

    private var allNews: List<NewWithGenre>? = null

    var currentCountry = CountryUtils.selectedCountry
        private set(value) {
            if (field == value) return
            field = value
            allNews = null
            setNews()
        }


    init {
        getAllNews()
        Log.d("tagz", "init search: ")
    }

    fun filterList(query: String?) {
//        sendAction(SearchActionBus.Loading)
        viewModelScope.launch {
            if (query == null) return@launch
            allNews?.let { list ->
                val filteredList = mutableListOf<NewWithGenre>()
                for (newWithGenre in list) {
                    if (newWithGenre.new.name.lowercase(Locale.ROOT)
                            .contains(query.lowercase(Locale.ROOT))
                    ) {
                        filteredList.add(newWithGenre)
                    }
                }
                sendAction(SearchActionBus.FilteringFinished(filteredList))
            }

        }
    }


    private fun getAllNews() {
        viewModelScope.launch {
            getAllNewsUseCase.execute(currentCountry).onEach {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        allNews = it.data
                    }
                }
            }.collect()
        }
    }

    fun setNews() {
        Log.d("tag", "setNewsSearch: $allNews")
        if (allNews == null)
            getAllNews()
    }

    fun setCurrentCountry() {
        currentCountry = CountryUtils.selectedCountry
    }

}
