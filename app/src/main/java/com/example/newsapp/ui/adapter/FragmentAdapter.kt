package com.example.newsapp.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.domain.model.enums.Tags
import com.example.newsapp.ui.main_activity.news_fragment.NewsFragment

class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val tags: List<Tags>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun createFragment(position: Int): Fragment {
        val newsFragment = NewsFragment().also {
            it.arguments = Bundle().apply {
                putParcelable("tag", tags[position])
            }
        }
        return newsFragment
    }

    override fun getItemCount(): Int {
        return tags.size
    }
}