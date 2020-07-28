package com.example.ara.atmospher.models.openWeather.oneCall

import com.google.gson.annotations.SerializedName

data class Snow(
        @SerializedName("snow")
        val snow: Double
)