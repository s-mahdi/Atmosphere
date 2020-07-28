package com.example.ara.atmospher.models.openWeather

import com.google.gson.annotations.SerializedName

data class Current(
        @SerializedName("coord")
        val coordination: Coord,

        @SerializedName("weather")
        val weatherList: List<Weather>,

        @SerializedName("base")
        val base: String,

        @SerializedName("main")
        val mainCondition: Main,

        @SerializedName("visibility")
        val visibility: String,

        @SerializedName("wind")
        val wind: Wind,

        @SerializedName("clouds")
        val clouds: Clouds,

        @SerializedName("dt")
        val info: Long,

        @SerializedName("sys")
        val sys: Sys,

        @SerializedName("timezone")
        val timezone: Int,

        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val cityName: String,

        @SerializedName("cod")
        val operationCod: Int

)

