package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    fun String.formatDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(this)
            val outputFormat = SimpleDateFormat("EEEE, HH:mm:ss, yyyy", Locale.getDefault())
            outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid Date"
        }
    }
}