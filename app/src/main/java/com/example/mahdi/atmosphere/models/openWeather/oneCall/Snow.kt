package com.example.mahdi.atmosphere.models.openWeather.oneCall

import com.google.gson.annotations.SerializedName

data class Snow(
        @SerializedName("snow")
        val snow: Double
)