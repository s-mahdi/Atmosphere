package com.example.ara.atmospher.retrofit;

import com.example.ara.atmospher.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("/data/2.5/weather")
    Call<WeatherData> weatherCall(@Query("q") String cityName, @Query("appid") String API_KEY);

    @GET("data/2.5/forecast/daily")
    Call<WeatherData> forecastCall(@Query("q") String cityName, @Query("lang") String lang, @Query("appid") String API_KEY);

}
