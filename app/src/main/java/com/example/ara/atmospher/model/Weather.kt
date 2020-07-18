package com.example.ara.atmospher.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
        @SerializedName("coord")
        val coordination: Coordination,

        @SerializedName("weather")
        val weatherList: List<Weather>,

        @SerializedName("base")
        val base: String,

        @SerializedName("main")
        val mainCondition: MainCondition,

        @SerializedName("visibility")
        val visibility: String,

        @SerializedName("wind")
        val wind: Wind,

        @SerializedName("clouds")
        val clouds: Clouds,

        @SerializedName("dt")
        val info: Long,

        @SerializedName("sys")
        val sunData: SunData,

        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val cityName: String,

        @SerializedName("code")
        val operationCod: Int,

        @SerializedName("message")
        val message: String

)

data class Coordination(
        @SerializedName("lon")
        val long: Double,

        @SerializedName("lat")
        val lat: Double
)

data class Weather(
        @SerializedName("id")
        val id: Int,

        @SerializedName("main")
        val main: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("icon")
        val icon: String
)

data class Clouds(
        @SerializedName("all")
        val all: Int
)

data class Wind(
        @SerializedName("speed")
        val speed: Double,

        @SerializedName("deg")
        val degree: Double
)

data class MainCondition(
        @SerializedName("temp")
        val temperature: Double,
        @SerializedName("pressure")
        val pressure: Double,

        @SerializedName("humidity")
        val humidity: Double,

        @SerializedName("temp_min")
        val temp_min: Double,

        @SerializedName("temp_max")
        val temp_max: Double
)

data class SunData(
        @SerializedName("type")
        val type: Int,
        @SerializedName("id")
        val id: Int,

        @SerializedName("message")
        val message: String,

        @SerializedName("country")
        val country: String,

        @SerializedName("sunrise")
        val sunrise: Long,

        @SerializedName("sunset")
        val sunset: Long
)
