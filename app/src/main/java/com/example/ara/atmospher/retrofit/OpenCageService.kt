package com.example.ara.atmospher.retrofit

import com.example.ara.atmospher.models.opencage.OpenCageResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenCageService {
    @GET("/geocode/v1/json")
    suspend fun searchCities(@Query("q") input: String,
                          @Query("key") key: String,
                          @Query("language") language: String): Response<OpenCageResult>
}
