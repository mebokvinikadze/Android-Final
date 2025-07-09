package com.example.androidfinal.util


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun todayDate(): String {
        val formatter = SimpleDateFormat("d MMMM h:mma", Locale.getDefault())
        return formatter.format(Date())
    }
}
