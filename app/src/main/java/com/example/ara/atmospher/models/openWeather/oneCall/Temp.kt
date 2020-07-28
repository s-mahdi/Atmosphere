package com.example.ara.atmospher.models.openWeather.oneCall

import com.google.gson.annotations.SerializedName

data class Temp(
        @SerializedName("day")
        val day: Double,

        @SerializedName("min")
        val min: Double,

        @SerializedName("max")
        val max: Double,

        @SerializedName("night")
        val night: Double,

        @SerializedName("eve")
        val eve: Double,

        @SerializedName("morn")
        val morn: Double
)