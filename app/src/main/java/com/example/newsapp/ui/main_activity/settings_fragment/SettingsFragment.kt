package com.example.newsapp.ui.main_activity.settings_fragment

import CountryAdapter
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.ui.base.BaseFragment
import com.example.newsapp.utils.CountryUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsActionBus, SettingsViewModel, FragmentSettingsBinding>(
        FragmentSettingsBinding::inflate, SettingsViewModel::class.java
    ) {
    private lateinit var adapter: CountryAdapter

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
        viewModel.setAndUpdateCountry()
    }

}