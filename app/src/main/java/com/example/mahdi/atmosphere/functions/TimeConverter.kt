package com.example.mahdi.atmosphere.functions

import java.util.*

fun getWeekDay(dt: Long): String? {
    val converter = java.text.SimpleDateFormat("E", Locale.US)
    return getFaWeekDay(converter.format(dt * 1000))
}

fun getFaWeekDay(day: String): String {
    return when (day) {
        "Sun" -> "یک‌شنبه"
        "Mon" -> "دوشنبه"
        "Tue" -> "سه‌شنبه"
        "Wed" -> "چهارشنبه"
        "Thu" -> "پنج‌شنبه"
        "Fri" -> "جمعه"
        "Sat" -> "شنبه"
        else -> "خطا"
    }
}