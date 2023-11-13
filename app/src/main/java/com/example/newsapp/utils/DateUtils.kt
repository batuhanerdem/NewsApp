package com.example.newsapp.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

object DateUtils {
    fun String.formatDateFull(): String {
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

    fun String.formatDateRelativeToToday(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(this)
            val currentDate = Calendar.getInstance().time

            when (val dayDifference = calculateDayDifference(currentDate, date!!)) {
                0L -> "Today"
                1L -> "Yesterday"
                else -> "$dayDifference days ago"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid Date"
        }
    }

    private fun calculateDayDifference(currentDate: Date, otherDate: Date): Long {
        val diffInMillies = abs(currentDate.time - otherDate.time)
        val (millisecInSec, secInMin, minInHour, hourInDay) = listOf(1000, 60, 60, 24)
        return diffInMillies / (millisecInSec * secInMin * minInHour * hourInDay)
    }
}