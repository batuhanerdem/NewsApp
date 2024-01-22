package com.example.newsapp.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

object DateUtils {
    private const val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun String.toDate(dateFormatPattern: String = DATE_FORMAT_PATTERN): Date {
        val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
        return dateFormat.parse(this)!!
    }

    fun String.formatDateRelativeToToday(dateFormatPattern: String = DATE_FORMAT_PATTERN): String {
        val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(this)
            val currentDate = Calendar.getInstance().time

            val dayDifference = calculateDayDifference(currentDate, date!!)
            val hourDifference = calculateHourDifference(currentDate, date)
            val minuteDifference = calculateMinuteDifference(currentDate, date)
            val secondDifference = calculateSecondDifference(currentDate, date)

            when (dayDifference) {
                0L -> when {
                    hourDifference > 0 -> "$hourDifference h"
                    minuteDifference > 0 -> "$minuteDifference min"
                    else -> {
                        "$secondDifference s"
                    }
                }

                1L -> "Yesterday"
                else -> "$dayDifference days"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid Date"
        }
    }

    fun String.isNewerThan(
        date: String,
        dateFormatPattern: String = DATE_FORMAT_PATTERN
    ): Boolean? {
        val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val myDate = dateFormat.parse(this)
        val otherDate = dateFormat.parse(date)
        if (myDate == null || otherDate == null) return null
        return myDate.time > otherDate.time
    }


    fun isFirstOlder(
        date1: String,
        date2: String,
        dateFormatPattern: String = DATE_FORMAT_PATTERN
    ): Boolean? {
        val format = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
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

    private fun calculateSecondDifference(currentDate: Date, otherDate: Date): Long {
        val diffInMillies = abs(currentDate.time - otherDate.time)
        val millisecInSec = 1000
        return diffInMillies / millisecInSec
    }
}
