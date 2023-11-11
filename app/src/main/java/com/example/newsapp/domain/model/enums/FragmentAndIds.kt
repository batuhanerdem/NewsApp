package com.example.newsapp.domain.model.enums

import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.ui.main_activity.holder_fragment.HolderFragment
import com.example.newsapp.ui.main_activity.settings_fragment.SettingsFragment

enum class FragmentAndIds(val fragment: Class<out Fragment>, val id: Int) {
    HOLDER(HolderFragment::class.java, R.id.holderFragment),
    SETTINGS(SettingsFragment::class.java, R.id.settingsFragment)
}