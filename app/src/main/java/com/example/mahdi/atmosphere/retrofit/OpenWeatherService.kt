package com.example.mahdi.atmosphere.retrofit

import com.example.mahdi.atmosphere.models.openWeather.Current
import com.example.mahdi.atmosphere.models.openWeather.Forecast5
import com.example.mahdi.atmosphere.models.openWeather.oneCall.OneCall
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("/data/2.5/onecall?units=metric")
    suspend fun oneCall(@Query("lat") lat: Double,
                        @Query("lon") lon: Double,
                        @Query("appid") API_KEY: String): Response<OneCall>?
}