package com.example.mahdi.atmosphere.models.openWeather

import com.google.gson.annotations.SerializedName

data class Coord(
        @SerializedName("lon")
        val long: Double,

        @SerializedName("lat")
        val lat: Double
)
