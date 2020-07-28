package com.example.ara.atmospher.models.openWeather

import com.google.gson.annotations.SerializedName

data class Clouds(
        @SerializedName("all")
        val all: Int
)
