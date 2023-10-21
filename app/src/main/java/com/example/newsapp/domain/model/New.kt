package com.example.newsapp.domain.model

data class New(
    val key: String,
    val name: String,
    val url: String,
    val description: String,
    val image: String,
    val source: String
)