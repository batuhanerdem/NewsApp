package com.example.newsapp.ui.main_activity.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.ui.adapter.CountryAdapter

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRV()
    }

    private fun setRV() {
        val adapter = CountryAdapter()
        binding.recyclerCountry.adapter = adapter
        binding.recyclerCountry.layoutManager = LinearLayoutManager(context)
    }
}