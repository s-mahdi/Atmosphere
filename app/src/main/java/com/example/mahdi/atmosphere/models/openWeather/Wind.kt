package com.example.mahdi.atmosphere.models.openWeather

import com.google.gson.annotations.SerializedName

data class Wind(
        @SerializedName("speed")
        val speed: Double,

        @SerializedName("deg")
        val degree: Double
)
