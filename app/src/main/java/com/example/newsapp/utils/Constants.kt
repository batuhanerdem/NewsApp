package com.example.newsapp.utils

import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.domain.model.enums.Tags

object Constants {
    const val BASE_URL = "https://api.collectapi.com"
    fun getTags(): List<Tags> {
        return enumValues<Tags>().toList()
    }

    fun getCountries(): List<Countries> {
        return enumValues<Countries>().toList()
    }
}