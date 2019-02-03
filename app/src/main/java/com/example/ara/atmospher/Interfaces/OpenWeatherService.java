package com.example.ara.atmospher.Interfaces;

import com.example.ara.atmospher.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("/data/2.5/weather")
    Call<WeatherData> weatherCall(@Query("q") String cityName, @Query("appid") String API_KEY);

}
