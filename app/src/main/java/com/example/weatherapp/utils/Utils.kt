package com.example.weatherapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d y")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

@SuppressLint("SimpleDateFormat")
fun formatDateWeek(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

@SuppressLint("SimpleDateFormat")
fun formateDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm aa")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formateDecimals(item: Double): String {
    return " %.0f".format(item)
}