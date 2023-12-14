package com.example.newsapp.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

object DateUtils {
    private const val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun String.formatDateRelativeToToday(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(this)
            val currentDate = Calendar.getInstance().time

            val dayDifference = calculateDayDifference(currentDate, date!!)
            val hourDifference = calculateHourDifference(currentDate, date)
            val minuteDifference = calculateMinuteDifference(currentDate, date)

            when (dayDifference) {
                0L -> when {
                    hourDifference > 0 -> "$hourDifference h"
                    minuteDifference > 0 -> "$minuteDifference min"
                    else -> "1 min"
                }

                1L -> "Yesterday"
                else -> "$dayDifference days"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid Date"
        }
    }

    fun isFirstOlder(date1: String, date2: String): Boolean? {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        return try {
            val parsedDate1: Date = format.parse(date1) ?: Date()
            val parsedDate2: Date = format.parse(date2) ?: Date()

            parsedDate1.after(parsedDate2)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun calculateDayDifference(currentDate: Date, otherDate: Date): Long {
        val diffInMillies = abs(currentDate.time - otherDate.time)
        val (millisecInSec, secInMin, minInHour, hourInDay) = listOf(1000, 60, 60, 24)
        return diffInMillies / (millisecInSec * secInMin * minInHour * hourInDay)
    }

    private fun calculateHourDifference(currentDate: Date, otherDate: Date): Long {
        val diffInMillies = abs(currentDate.time - otherDate.time)
        val (millisecInSec, secInMin, minInHour) = listOf(1000, 60, 60)
        return diffInMillies / (millisecInSec * secInMin * minInHour)
    }

    private fun calculateMinuteDifference(currentDate: Date, otherDate: Date): Long {
        val diffInMillies = abs(currentDate.time - otherDate.time)
        val (millisecInSec, secInMin) = listOf(1000, 60)
        return diffInMillies / (millisecInSec * secInMin)
    }
}
