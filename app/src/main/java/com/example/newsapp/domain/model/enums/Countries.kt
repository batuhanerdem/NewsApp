package com.example.newsapp.domain.model.enums

import com.example.newsapp.R

enum class Countries(val value: String, val flagImageId: Int, var isSelected: Boolean) {
    TURKEY("tr", R.drawable.turkey, true),
    DEUTSCHLAND("de", R.drawable.deutschland, false),
    ENGLAND("en", R.drawable.england, false),
    RUSSIA("ru", R.drawable.russia, false)
}