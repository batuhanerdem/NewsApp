package com.example.newsapp.ui.main_activity.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnNews.setOnClickListener {
            goToNews()
        }
        binding.btnSettings.setOnClickListener {
            goToSettings()
        }
    }

    private fun goToNews() {
        val action = HomeFragmentDirections.actionHomeFragmentToHolderFragment()// todo bak
        view?.findNavController()?.navigate(action)
    }

    private fun goToSettings() {
        val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
        view?.findNavController()?.navigate(action)
    }
}