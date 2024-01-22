package com.example.newsapp.data.local.service

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.domain.model.enums.Countries
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreService @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        const val PREFERENCE_NAME = "NewsApp"
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = PREFERENCE_NAME,
        )
        val SELECTED_COUNTRY = stringPreferencesKey("selectedCountry")
    }

    suspend fun updateSelectedCountry(countryId: Int) {
        val value = countryId.toString()
        context.dataStore.edit { preferences ->
            preferences[SELECTED_COUNTRY] = value
        }
    }

    fun getSelectedCountry(): Flow<Countries?> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e("DataStore", "Error reading preferences", exception)
            emit(emptyPreferences())
        } else {
            Log.e("DataStore", "Unexpected error", exception)
            throw exception
        }
    }.map { preferences ->
        val selectedCountryId = preferences[SELECTED_COUNTRY]
        findCountryById(selectedCountryId)
    }

    private fun findCountryById(id: String?): Countries? {
        val intId = id?.toInt()
        Countries.entries.map {
            if (it.id == intId) return it
        }
        return null
    }
}