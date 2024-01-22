package com.example.newsapp.ui.main_activity.splash_screen_fragment

import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.repository.DataStoreRepository
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.ui.base.BaseViewModel
import com.example.newsapp.utils.CountryUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    BaseViewModel<SplashScreenActionBus>() {
    private val DEFAULT_COUNTRY = Countries.TURKEY

    fun getCountryFromDataStore() {
        viewModelScope.launch {
            dataStoreRepository.getSelectedCountry().collect {
                if (it == null) {
                    insertDefaultCountry()
                    return@collect
                }
                CountryUtils.selectedCountry = it
                sendAction(SplashScreenActionBus.DataLoaded)
            }
        }
    }

    private suspend fun insertDefaultCountry() {
        dataStoreRepository.updateSelectedCountry(DEFAULT_COUNTRY.id)
        CountryUtils.selectedCountry = DEFAULT_COUNTRY
        sendAction(SplashScreenActionBus.DataLoaded)
    }
}