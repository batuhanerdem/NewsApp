package com.example.newsapp.ui.main_activity.holder_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHolderBinding
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.adapter.FragmentAdapter
import com.example.newsapp.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator

class HolderFragment : Fragment() {
    private lateinit var binding: FragmentHolderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHolderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }


    private fun setUI() {
        val adapter = FragmentAdapter(this.parentFragmentManager, this.lifecycle)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val tags = Constants.TAG_LIST
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tags[position].title
        }.attach()
    }
}