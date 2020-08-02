package com.example.ara.atmospher.models.openWeather.oneCall

import com.example.ara.atmospher.models.openWeather.Weather
import com.google.gson.annotations.SerializedName

data class Current(
        @SerializedName("dt")
        val dt: Long,

        @SerializedName("sunrise")
        val sunrise: Long,

        @SerializedName("sunset")
        val sunset: Long,

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

        @SerializedName("uvi")
        val uvi: Double,

        @SerializedName("clouds")
        val clouds: Int,

        @SerializedName("visibility")
        val visibility: Int,

        @SerializedName("wind_speed")
        val windSpeed: Double,

        @SerializedName("wind_degree")
        val windDegree: Double,

        @SerializedName("weather")
        val weatherList: List<Weather>,

        @SerializedName("rain")
        val rain: Rain,

        @SerializedName("snow")
        val snow: Snow
)
