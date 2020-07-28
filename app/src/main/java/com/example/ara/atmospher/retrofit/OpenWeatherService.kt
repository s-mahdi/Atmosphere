package com.example.ara.atmospher.retrofit

import com.example.ara.atmospher.models.openWeather.Current
import com.example.ara.atmospher.models.openWeather.Forecast5
import com.example.ara.atmospher.models.openWeather.oneCall.OneCall
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("q") cityName: String, @Query("appid") API_KEY: String): Response<Current>

    @GET("/data/2.5/forecast")
    suspend fun getForecast(@Query("q") cityName: String, @Query("appid") API_KEY: String): Response<Forecast5>

    @GET("/data/2.5/onecall?units=metric")
    suspend fun oneCall(@Query("lat") lat: Double,
                        @Query("lon") lon: Double,
                        @Query("appid") API_KEY: String): Response<OneCall>
}