package com.example.ara.atmospher.models.openWeather

import com.google.gson.annotations.SerializedName

data class Main(
        @SerializedName("temp")
        val temperature: Double,

        @SerializedName("feels_like")
        val feelsLike: Double,

        @SerializedName("temp_min")
        val temp_min: Double,

        @SerializedName("temp_max")
        val temp_max: Double,

        @SerializedName("pressure")
        val pressure: Double,

        @SerializedName("sea_level")
        val seaLevel: Int,

        @SerializedName("grnd_level")
        val grndLevel: Int,

        @SerializedName("humidity")
        val humidity: Double,

        @SerializedName("temp_kf")
        val tempKf: Double
)
