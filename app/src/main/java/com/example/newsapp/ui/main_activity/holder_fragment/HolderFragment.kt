package com.example.newsapp.ui.main_activity.holder_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentHolderBinding
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.ui.adapter.FragmentAdapter
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
        val adapter = FragmentAdapter(this.childFragmentManager, this.lifecycle)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val tags = enumValues<Tags>().toList()
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tags[position].title
        }.attach()
    }
}