package com.example.ara.atmospher.models.openWeather

import com.google.gson.annotations.SerializedName

data class Sys(
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
