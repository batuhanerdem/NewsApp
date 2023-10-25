package com.example.newsapp.utils

import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.domain.model.enums.Tags
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class Constants {
    companion object {
        const val BASE_URL = "https://api.collectapi.com"
        fun getTags(): List<Tags> {
            return enumValues<Tags>().toList()
        }

        fun getCountries(): List<Countries> {
            return enumValues<Countries>().toList()
        }

        fun String.formatDate(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")

            return try {
                val date = dateFormat.parse(this)
                val outputFormat = SimpleDateFormat("EEEE, HH:mm:ss, yyyy", Locale.getDefault())
                outputFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                "Invalid Date"
            }
        }

    }

}