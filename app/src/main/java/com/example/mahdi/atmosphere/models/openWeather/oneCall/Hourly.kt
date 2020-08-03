package com.example.mahdi.atmosphere.models.openWeather.oneCall

import com.example.mahdi.atmosphere.models.openWeather.Weather
import com.google.gson.annotations.SerializedName

data class Hourly(
        @SerializedName("dt")
        val dt: Long,

        @SerializedName("temp")
        val temp: Double,

        @SerializedName("feels_like")
        val feelsLike: Double,

        @SerializedName("pressure")
        val pressure: Int,

        @SerializedName("humidity")
        val humidity: Int,

        @SerializedName("dew_point")
        val dewPoint: Double,

        @SerializedName("clouds")
        val clouds: Int,

        @SerializedName("visibility")
        val visibility: Int,

        @SerializedName("wind_speed")
        val windSpeed: Double,

        @SerializedName("wind_degree")
        val windDegree: Double,

        @SerializedName("pop")
        val pop: Double,

        @SerializedName("weather")
        val weatherList: List<Weather>,

        @SerializedName("rain")
        val rain: Rain,

        @SerializedName("snow")
        val snow: Snow
)