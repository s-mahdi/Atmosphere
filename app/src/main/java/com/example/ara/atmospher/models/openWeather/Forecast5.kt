package com.example.ara.atmospher.models.openWeather

import com.google.gson.annotations.SerializedName

data class Forecast5(
        @SerializedName("cod")
        val operationCod: Int,

        @SerializedName("message")
        val message: Int,

        @SerializedName("cnt")
        val cnt: Int,

        @SerializedName("list")
        val list: List<ForecastList>,

        @SerializedName("city")
        val city: City
)

