package com.example.newsapp.ui.main_activity.settings_fragment

import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.ui.base.BaseActionBus
import com.example.newsapp.utils.SelectableData

sealed interface SettingsActionBus : BaseActionBus {
    data object Init : SettingsActionBus

    data class CountriesLoaded(val countryList: List<SelectableData<Countries>>) : SettingsActionBus

}