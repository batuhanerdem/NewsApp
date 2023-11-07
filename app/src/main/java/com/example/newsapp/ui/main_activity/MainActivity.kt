package com.example.newsapp.ui.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

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