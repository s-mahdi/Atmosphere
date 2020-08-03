package com.example.mahdi.atmosphere.models.openWeather.oneCall

import com.google.gson.annotations.SerializedName

data class Rain (
        @SerializedName("1h")
        val rain: Double
)