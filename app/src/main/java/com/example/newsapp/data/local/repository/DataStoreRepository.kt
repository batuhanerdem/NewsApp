package com.example.newsapp.data.local.repository

import com.example.newsapp.data.local.service.DataStoreService
import com.example.newsapp.domain.model.enums.Countries
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class DataStoreRepository @Inject constructor(private val preferences: DataStoreService) {

    suspend fun updateSelectedCountry(value: Int) {
        preferences.updateSelectedCountry(value)
    }

    fun getSelectedCountry(): Flow<Countries?> {
        return preferences.getSelectedCountry()
    }

}