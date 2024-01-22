package com.example.newsapp.ui.main_activity.home_fragment

import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.ui.base.BaseActionBus

sealed interface HomeActionBus : BaseActionBus {

    data object Init : HomeActionBus

    data class ShowErrorMessage(val errorMessage: String? = null) : HomeActionBus

    data object Loading : HomeActionBus

    data class NewsLoaded(val list: List<NewWithGenre>) : HomeActionBus

    data class SingleNewLoaded(val new: NewWithGenre) : HomeActionBus

}