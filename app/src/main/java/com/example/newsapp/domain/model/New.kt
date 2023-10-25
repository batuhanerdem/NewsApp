package com.example.newsapp.domain.model

import java.io.Serializable

data class New(
    val key: String,
    val name: String,
    val url: String,
    val description: String,
    val image: String,
    val source: String,
    val date: String
) : Serializable