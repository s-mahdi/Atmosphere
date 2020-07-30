package com.example.ara.atmospher.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val openWeatherService: OpenWeatherService by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return@lazy retrofit.create(OpenWeatherService::class.java)
    }

    val openCageService: OpenCageService by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.opencagedata.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return@lazy retrofit.create(OpenCageService::class.java)
    }
}