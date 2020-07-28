package com.example.ara.atmospher.models.openWeather

import com.google.gson.annotations.SerializedName

data class ForecastList(
        @SerializedName("dt")
        val dt: Long,

        @SerializedName("main")
        val main: Main,

        @SerializedName("weather")
        val weather: List<Weather>,

        @SerializedName("clouds")
        val clouds: Clouds,

        @SerializedName("wind")
        val wind: Wind,

        @SerializedName("dt_txt")
        val dtTxt: String
)
