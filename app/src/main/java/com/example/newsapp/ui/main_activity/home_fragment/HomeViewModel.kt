package com.example.newsapp.ui.main_activity.home_fragment

import android.os.Parcel
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.domain.use_case.GetRecentNewsUseCase
import com.example.newsapp.ui.base.BaseViewModel
import com.example.newsapp.utils.CountryUtils
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var getRecentNewsUseCase: GetRecentNewsUseCase) :
    BaseViewModel<HomeActionBus>() {

    private var news: MutableList<NewWithGenre>? = null
        set(value) {
            if (field != value) field = value
        }

    private var singleNew: NewWithGenre? = null
        set(value) {
            if (field != value) field = value
        }
    private var currentCountry: Countries = CountryUtils.selectedCountry
        set(value) {
            if (field == value) return
            field = value
            setNewsAndSingleNewNull()
            setNews()
        }

    init {
        Log.d("tag", "init: ")
        sendAction(HomeActionBus.Init)

    }
    fun setCurrentCountry() {
        currentCountry = CountryUtils.selectedCountry
    }

    fun setNews() {
        Log.d("tag", "setNewsHome: $news")
        sendAction(HomeActionBus.Loading)
        val result = news?.also { news ->
            singleNew?.let { singleNew ->
                sendNewActions(news, singleNew)
            }
        }
        if (result == null) getRecentNews()
    }

    private fun getRecentNews() {
        viewModelScope.launch {
            getRecentNewsUseCase.execute(currentCountry).collect {
                when (it) {
                    is Resource.Error -> sendAction(HomeActionBus.ShowErrorMessage(it.message))
                    is Resource.Loading -> sendAction(HomeActionBus.Loading)
                    is Resource.Success -> {
                        it.data?.let { list ->
                            val (_news, _singleNew) = list.takeFirstNewFromList()
                            setNewsAndSingleNew(_news, _singleNew)
                        }
                    }
                }
            }
        }
    }

    private fun List<NewWithGenre>.takeFirstNewFromList(): Pair<MutableList<NewWithGenre>, NewWithGenre> {
        if (this.isEmpty()) return Pair(mutableListOf(), NewWithGenre(Parcel.obtain()))
        val _mutableNewList = mutableListOf<NewWithGenre>()
        _mutableNewList.addAll(this)
        val _singleNew = _mutableNewList[0]
        _mutableNewList.removeAt(0)
        return Pair(_mutableNewList, _singleNew)
    }

    private fun setNewsAndSingleNew(newList: MutableList<NewWithGenre>, singleNew: NewWithGenre) {
        news = newList
        this.singleNew = singleNew
        sendNewActions(newList, singleNew)
    }

    private fun sendNewActions(newList: MutableList<NewWithGenre>, singleNew: NewWithGenre) {
        sendAction(HomeActionBus.SingleNewLoaded(singleNew))
        sendAction(HomeActionBus.NewsLoaded(newList))
    }

    private fun setNewsAndSingleNewNull() {
        news = null
        singleNew = null
    }

}