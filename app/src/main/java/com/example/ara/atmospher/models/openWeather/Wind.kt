package com.example.ara.atmospher.models.openWeather

import com.google.gson.annotations.SerializedName

data class Wind(
        @SerializedName("speed")
        val speed: Double,

        @SerializedName("deg")
        val degree: Double
)
