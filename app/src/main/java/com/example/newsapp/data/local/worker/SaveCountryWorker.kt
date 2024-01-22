package com.example.newsapp.data.local.worker

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsapp.data.local.repository.DataStoreRepository
import com.example.newsapp.data.local.service.DataStoreService
import com.example.newsapp.data.local.service.DataStoreService.Companion.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCountryWorker @Inject constructor(
    private val context: Context,
    private val params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
//            delay(5000)
            Log.d("tag", "doWork: ")
            val countryId = params.inputData.getInt(SELECTED_COUNTRY_ID, 0)
            context.dataStore.edit { preferences ->
                Log.d("tag", "edit: ")
                preferences[DataStoreService.SELECTED_COUNTRY] = countryId.toString()
            }
            Result.success()
        }

    }

    companion object {
        const val SELECTED_COUNTRY_ID = "SELECTED_COUNTRY_ID"
    }
}