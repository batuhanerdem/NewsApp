package com.example.newsapp.ui.main_activity.settings_fragment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.repository.DataStoreRepository
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.ui.base.BaseViewModel
import com.example.newsapp.utils.CountryUtils
import com.example.newsapp.utils.CountryUtils.toSelectableData
import com.example.newsapp.utils.SelectableData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    BaseViewModel<SettingsActionBus>() {

    private var currentCountry: Countries = CountryUtils.selectedCountry
        set(value) {
            if (field == value) return
            field = value
            getSelectableCountries()
        }
    init {
        Log.d("tag", "init settings: ")
    }

    fun getSelectableCountries() {
        val countriesList = enumValues<Countries>().toList()
        val selectableCountries = mutableListOf<SelectableData<Countries>>()
        for (country in countriesList) {
            selectableCountries.add(country.toSelectableData())
        }
        sendAction(SettingsActionBus.CountriesLoaded(selectableCountries))
    }

    fun setAndUpdateCountry() {
        currentCountry = CountryUtils.selectedCountry
        viewModelScope.launch {
            dataStoreRepository.updateSelectedCountry(currentCountry.id)
        }
    }

}