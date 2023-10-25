package com.example.newsapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.main_activity.news_fragment.NewsFragment
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Country

class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val tags = Constants.getTags()
    override fun getItemCount(): Int {
        return tags.size
    }

    override fun createFragment(position: Int): Fragment {
        return NewsFragment(tags[position], Country.selectedCountry)
    }
}
