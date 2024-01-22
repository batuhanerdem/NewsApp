package com.example.newsapp.ui.main_activity.news_fragment

import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.ui.base.BaseActionBus

sealed interface NewsActionBus : BaseActionBus {
    data object Init : NewsActionBus

    data class ShowErrorMessage(val errorMessage: String?) : NewsActionBus

    data class NewsLoaded(val newList: List<NewWithGenre>) : NewsActionBus

    data object Loading : NewsActionBus
}