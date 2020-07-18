package com.example.ara.atmospher.functions

import com.example.ara.atmospher.R

fun getBackground(statusId: Int): Int {
    return when (statusId / 100) {
        2 -> R.drawable.thunderstorm
        3 -> R.drawable.drizzle
        5 -> R.drawable.rainy_3
        6 -> R.drawable.snowy_2
        7 -> R.drawable.foggy_2
        8 -> R.drawable.sunny
        else -> 0
    }
}
