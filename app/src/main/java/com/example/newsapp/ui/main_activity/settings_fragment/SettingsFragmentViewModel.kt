package com.example.newsapp.ui.main_activity.settings_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.model.SelectableData
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor() : ViewModel() {
    val selectableCountryList = MutableLiveData(getSelectableCountries())
    private fun getSelectableCountries(): MutableList<SelectableData<Countries>> {
        val countriesList = enumValues<Countries>().toList()
        val selectableCountries = mutableListOf<SelectableData<Countries>>()
        for (country in countriesList) {
            selectableCountries.add(country.toSelectableData())
        }
        return selectableCountries
    }

    private fun Countries.toSelectableData(): SelectableData<Countries> {
        val isSelected = Constants.selectedCountry == this
        return SelectableData(this, isSelected)
    }
}