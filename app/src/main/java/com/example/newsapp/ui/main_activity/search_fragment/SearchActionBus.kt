package com.example.newsapp.ui.main_activity.search_fragment

import com.example.newsapp.domain.model.NewWithGenre
import com.example.newsapp.ui.base.BaseActionBus

sealed interface SearchActionBus : BaseActionBus {
    data object Init : SearchActionBus

    data object Loading : SearchActionBus

    data class FilteringFinished(val filteredList: List<NewWithGenre>) : SearchActionBus
    data class ShowErrorMessage(val errorMessage: String?) : SearchActionBus

}