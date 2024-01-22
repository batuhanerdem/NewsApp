package com.example.newsapp.ui.main_activity.settings_fragment

import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.ui.base.BaseViewModel
import com.example.newsapp.utils.CountryUtils
import com.example.newsapp.utils.CountryUtils.toSelectableData
import com.example.newsapp.utils.SelectableData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel<SettingsActionBus>() {

    private var currentCountry: Countries? = null
        set(value) {
            if (field == value) return
            field = value
            getSelectableCountries()
        }

    fun getSelectableCountries() {
        val countriesList = enumValues<Countries>().toList()
        val selectableCountries = mutableListOf<SelectableData<Countries>>()
        for (country in countriesList) {
            selectableCountries.add(country.toSelectableData())
        }
        sendAction(SettingsActionBus.CountriesLoaded(selectableCountries))
    }

    fun setCurrentCountry() {
        currentCountry = CountryUtils.selectedCountry
    }

}