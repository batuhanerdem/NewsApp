package com.example.newsapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.ui.main_activity.news_fragment.NewsFragment
import com.example.newsapp.utils.CurrentCountry

class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val tags: List<Tags>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun createFragment(position: Int): Fragment {
        return NewsFragment(tags[position], CurrentCountry.value)
    }

    override fun getItemCount(): Int {
        return tags.size
    }
}