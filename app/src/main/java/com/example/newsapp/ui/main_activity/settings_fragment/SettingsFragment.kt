package com.example.newsapp.ui.main_activity.settings_fragment

import CountryAdapter
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.newsapp.data.local.worker.SaveCountryWorker
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.ui.base.BaseFragment
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.CountryUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsActionBus, SettingsViewModel, FragmentSettingsBinding>(
        FragmentSettingsBinding::inflate,
        SettingsViewModel::class.java
    ) {
    private lateinit var adapter: CountryAdapter

    @Inject
    lateinit var workManager: WorkManager

    override fun initPage() {
        setRV()
        viewModel.getSelectableCountries()
    }

    override suspend fun onAction(action: SettingsActionBus) {
        when (action) {
            SettingsActionBus.Init -> {}

            is SettingsActionBus.CountriesLoaded -> {
                adapter.submitList(action.countryList)
            }
        }
    }

    private fun setRV() {
        adapter = CountryAdapter { country -> countryOnClick(country) }
        binding.recyclerCountry.adapter = adapter
    }

    private fun countryOnClick(country: Countries) {
        CountryUtils.selectedCountry = country
        viewModel.setCurrentCountry()
        val country = CountryUtils.selectedCountry
        val request = OneTimeWorkRequestBuilder<SaveCountryWorker>().setInputData(
            workDataOf(
                SaveCountryWorker.SELECTED_COUNTRY_ID to country.id
            )
        ).build()
//        workManager.enqueue(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        val country = CountryUtils.selectedCountry
        val request = OneTimeWorkRequestBuilder<SaveCountryWorker>().setInputData(
            workDataOf(
                SaveCountryWorker.SELECTED_COUNTRY_ID to country.id
            )
        ).build()
        workManager.enqueue(request)
        Log.d("tag", "onDestroy: ")

    }
}