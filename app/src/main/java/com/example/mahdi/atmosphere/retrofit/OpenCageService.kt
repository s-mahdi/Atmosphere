package com.example.mahdi.atmosphere.retrofit

import com.example.mahdi.atmosphere.models.opencage.OpenCageResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenCageService {
    @GET("/geocode/v1/json")
    suspend fun searchCities(@Query("q") input: String,
                          @Query("key") key: String,
                          @Query("language") language: String): Response<OpenCageResult>
}
