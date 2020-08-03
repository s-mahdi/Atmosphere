package com.example.mahdi.atmosphere.functions

import com.example.mahdi.atmosphere.R

fun getBackground(statusId: Int): Int {
    return when (statusId) {
        200, 201, 202, 210, 211, 212, 221, 230, 231, 232 -> R.drawable.thunderstorm
        300, 301, 302, 310, 311, 312, 313, 314, 321 -> R.drawable.drizzle
        522, 500, 501, 502, 503, 504, 520, 521, 531 -> R.drawable.rainy_3
        600, 601, 602, 611, 612, 615, 616, 620, 621, 622 -> R.drawable.snowy_2
        781, 771, 762, 761, 751, 741, 731, 721, 711, 701 -> R.drawable.foggy_2
        800 -> R.drawable.sunny
        801 -> R.drawable.scattered_clouds
        802 -> R.drawable.about_cloudy
        803, 804 -> R.drawable.cloudy
        else -> R.drawable.sunny
    }
}
