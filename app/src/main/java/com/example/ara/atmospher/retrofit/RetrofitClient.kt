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
}