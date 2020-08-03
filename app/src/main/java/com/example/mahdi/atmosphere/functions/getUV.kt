package com.example.mahdi.atmosphere.functions

fun getUV(uvi: Int): String {
    val values = arrayOf("پایین", "متوسط", "بالا", "بسیار بالا", "شدید")
    return when (uvi){
        in 0..2 -> values[0]
        in 3..5 -> values[1]
        in 6..7 -> values[2]
        in 8..10 -> values[3]
        else -> values[4]
    }.plus(" ($uvi)")
}