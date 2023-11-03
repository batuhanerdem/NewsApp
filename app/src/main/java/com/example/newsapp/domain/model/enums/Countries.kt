package com.example.newsapp.domain.model.enums

import com.example.newsapp.R

enum class Countries(val value: String, val flagImageId: Int) {
    TURKEY("tr", R.drawable.turkey),
    DEUTSCHLAND("de", R.drawable.deutschland),
    ENGLAND("en", R.drawable.england),
    RUSSIA("ru", R.drawable.russia)
}