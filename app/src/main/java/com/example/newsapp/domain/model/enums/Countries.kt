package com.example.newsapp.domain.model.enums

import com.example.newsapp.R

enum class Countries(val value: String, val flagImageId: Int) {
    TURKEY("tr", R.drawable.country_turkey),
    DEUTSCHLAND("de", R.drawable.country_deutschland),
    ENGLAND("en", R.drawable.country_england),
    RUSSIA("ru", R.drawable.country_russia)
}