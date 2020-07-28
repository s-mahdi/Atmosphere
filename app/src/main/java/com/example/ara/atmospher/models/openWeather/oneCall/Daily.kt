package com.example.ara.atmospher.models.openWeather.oneCall

import com.example.ara.atmospher.models.openWeather.Weather
import com.google.gson.annotations.SerializedName

data class Daily(
        @SerializedName("dt")
        val dt: Long,

        @SerializedName("sunrise")
        val sunrise: Long,

        @SerializedName("sunset")
        val sunset: Long,

        @SerializedName("temp")
        val temp: Temp,

        @SerializedName("feels_like")
        val feelsLike: FeelsLike,

        @SerializedName("pressure")
        val pressure: Int,

        @SerializedName("humidity")
        val humidity: Int,

        @SerializedName("dew_point")
        val dewPoint: Double,

        @SerializedName("wind_speed")
        val windSpeed: Double,

        @SerializedName("wind_degree")
        val windDegree: Double,

        @SerializedName("clouds")
        val clouds: Int,

        @SerializedName("uvi")
        val uvi: Double,

        @SerializedName("visibility")
        val visibility: Int,

        @SerializedName("pop")
        val pop: Double,

        @SerializedName("rain")
        val rain: Double,

        @SerializedName("snow")
        val snow: Double,

        @SerializedName("weather")
        val weatherList: List<Weather>

)