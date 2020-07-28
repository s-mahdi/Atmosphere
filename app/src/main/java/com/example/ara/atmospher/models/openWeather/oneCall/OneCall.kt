package com.example.ara.atmospher.models.openWeather.oneCall

import com.google.gson.annotations.SerializedName

data class OneCall(
        @SerializedName("current")
        val current: Current,

        @SerializedName("hourly")
        val hourly: List<Hourly>,

        @SerializedName("daily")
        val daily: List<Daily>
)

