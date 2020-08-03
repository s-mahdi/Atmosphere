package com.example.mahdi.atmosphere.functions

import com.example.mahdi.atmosphere.R

fun getIcon(statusId: Int): Int {
    return when (statusId) {
        200, 201, 202, 210, 211, 212, 221, 230, 231, 232 -> R.drawable.ic_thunderstorm
        300, 301, 302, 310, 311, 312, 313, 314, 321 -> R.drawable.ic_showr_rain
        522, 500, 501, 502, 503, 504, 520, 521, 531 -> R.drawable.ic_rain
        600, 601, 602, 611, 612, 615, 616, 620, 621, 622 -> R.drawable.ic_snow
        781, 771, 762, 761, 751, 741, 731, 721, 711, 701 -> R.drawable.ic_mist
        800 -> R.drawable.ic_clear_sky
        801 -> R.drawable.ic_few_clouds
        802 -> R.drawable.ic_scattered_clouds
        803, 804 -> R.drawable.ic_broken_clouds
        else -> 0
    }
}
