package com.example.ara.atmospher.retrofit

import com.example.ara.atmospher.model.WeatherData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("q") cityName: String, @Query("appid") API_KEY: String): Response<WeatherData>

    @GET("data/2.5/forecast/daily")
    fun forecastCall(@Query("q") cityName: String?, @Query("lang") lang: String?, @Query("appid") API_KEY: String?): Call<WeatherData?>?
}