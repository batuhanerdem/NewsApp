package com.example.newsapp.ui.main_activity.news_fragment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.domain.use_case.GetNewsUseCase
import com.example.newsapp.ui.base.BaseViewModel
import com.example.newsapp.utils.CountryUtils
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val useCase: GetNewsUseCase) :
    BaseViewModel<NewsActionBus>() {

    private var news: List<NewWithGenre>? = null

    private var tag: Tags? = null

    private var currentCountry = CountryUtils.selectedCountry
        private set(value) {
            if (field == value) return
            field = value
            news = null
            setNews()
        }

    fun setCurrentCountry() {
        currentCountry = CountryUtils.selectedCountry
    }

    fun setNews() {
        Log.d("tag", "setNewsNews: $news")
        if (news == null) {
            tag?.let { tag ->
                getNews(currentCountry, tag)
            }
        } else {
            sendAction(NewsActionBus.NewsLoaded(news!!)) // gonna change this
        }

    }

    fun setTag(tag: Tags?) {
        this.tag = tag
    }

    private fun getNews(country: Countries, tag: Tags) {
        viewModelScope.launch {
            useCase.execute(country, tag).collect {
                when (it) {
                    is Resource.Error -> {
                        sendAction(NewsActionBus.ShowErrorMessage(it.message))
                    }

                    is Resource.Success -> {
                        it.data?.let { list ->
                            news = list
                            sendAction(NewsActionBus.NewsLoaded(list))
                        }
                    }

                    is Resource.Loading -> {
                        sendAction(NewsActionBus.Loading)
                    }
                }
            }
        }
    }
}