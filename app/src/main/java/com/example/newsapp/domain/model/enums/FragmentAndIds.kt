package com.example.newsapp.domain.model.enums

import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.ui.main_activity.search_fragment.SearchFragment
import com.example.newsapp.ui.main_activity.settings_fragment.SettingsFragment

enum class FragmentAndIds(val fragment: Class<out Fragment>, val id: Int) {
    HOLDER(SearchFragment::class.java, R.id.searchFragment),
    SETTINGS(SettingsFragment::class.java, R.id.settingsFragment)
}