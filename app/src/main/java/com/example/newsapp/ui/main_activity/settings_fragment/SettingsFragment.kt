package com.example.newsapp.ui.main_activity.settings_fragment

import CountryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.domain.model.SelectableData
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: CountryAdapter
    private val viewModel: SettingsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRV()
        viewModel.selectableCountryList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }
    }

    private fun setRV() {
        adapter = CountryAdapter { country -> countryOnClick(country) }
        binding.recyclerCountry.adapter = adapter
        binding.recyclerCountry.layoutManager = LinearLayoutManager(context)
    }

    private fun countryOnClick(
        selectedCountry: SelectableData<Countries>,
    ) {
        Constants.selectedCountry = selectedCountry.data
        val list = viewModel.selectableCountryList.value
        list?.let {
            val previousSelectedDataIndex = it.indexOfFirst { country ->
                country.isSelected
            }
            val nextSelectedDataIndex = it.indexOf(selectedCountry)
            if (nextSelectedDataIndex == -1) return
            val previousData = it[previousSelectedDataIndex].data
            it[previousSelectedDataIndex] = SelectableData(
                previousData, false
            )
            it[nextSelectedDataIndex] = SelectableData(selectedCountry.data, true)
            viewModel.selectableCountryList.value = it
        }
    }
}