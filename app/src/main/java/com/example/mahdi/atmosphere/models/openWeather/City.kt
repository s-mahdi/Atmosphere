package com.example.mahdi.atmosphere.models.openWeather

import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("coord")
        val coord: Coord,

        @SerializedName("country")
        val country: String,

        @SerializedName("timezone")
        val timezone: Int,

        @SerializedName("sunrise")
        val sunrise: Long,

        @SerializedName("sunset")
        val sunset: Long,

        @SerializedName("type")
        val type: Int,

        @SerializedName("message")
        val message: String
)