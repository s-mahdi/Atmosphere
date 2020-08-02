package com.example.ara.atmospher.functions

import kotlin.math.roundToInt

fun getWindDirection(deg: Double): String {
    val directions = arrayOf("شمال", "شمال شرق", "شرق", "جنوب شرق", "جنوب", "جنوب غرب", "غرب", "شمال غرب")
    return directions[ (((deg % 360) / 45).roundToInt() % 8) ]
}