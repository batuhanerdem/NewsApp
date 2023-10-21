package com.example.newsapp.domain.model

data class APIResponse(
    val result: List<New>,
    val success: Boolean
)