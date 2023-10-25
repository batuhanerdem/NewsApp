package com.example.newsapp.utils

import com.example.newsapp.domain.model.enums.Tags

object Constants {
    val BASE_URL = "https://api.collectapi.com"
    var TAG_LIST: List<Tags> = enumValues<Tags>().toList()
}