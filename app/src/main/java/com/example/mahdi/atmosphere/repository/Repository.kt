package com.example.mahdi.atmosphere.repository

import androidx.lifecycle.LiveData
import com.example.mahdi.atmosphere.models.openWeather.Current
import com.example.mahdi.atmosphere.models.openWeather.Forecast5
import com.example.mahdi.atmosphere.models.openWeather.oneCall.OneCall
import com.example.mahdi.atmosphere.models.opencage.OpenCageResult
import com.example.mahdi.atmosphere.retrofit.RetrofitClient
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    var job: CompletableJob? = null
    private const val openWeatherKey = "ece8f3c084bf15aef779da23422b4aab"
    private const val openCageKey = "5f484667ba264bedbef60ff442b75270"

    fun getWeather(cityName: String): LiveData<Current?> {
        job = Job()
        return object : LiveData<Current?>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val weatherData = RetrofitClient.openWeatherService.getWeather(cityName, openWeatherKey)
                        withContext(Main) {
                            if (weatherData.isSuccessful) {
                                value = weatherData.body()
                                theJob.complete()
                            } else {
                                value = null
                                theJob.cancel()
                            }
                        }
                    }
                }

            }
        }
    }

    fun getForecast(cityName: String): LiveData<Forecast5?> {
        job = Job()
        return object : LiveData<Forecast5?>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val response = RetrofitClient.openWeatherService.getForecast(cityName, openWeatherKey)
                        withContext(Main) {
                            if (response.isSuccessful) {
                                value = response.body()
                                theJob.complete()
                            } else {
                                value = null
                                theJob.cancel()
                            }
                        }
                    }
                }
            }
        }
    }

    fun oneCall(lat: Double, lon: Double): LiveData<OneCall?> {
        job = Job()
        return object : LiveData<OneCall?>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val response = RetrofitClient.openWeatherService.oneCall(lat, lon, openWeatherKey)
                        withContext(Main) {
                            if (response.isSuccessful) {
                                value = response.body()
                                theJob.complete()
                            } else {
                                value = null
                                theJob.cancel()
                            }
                        }
                    }
                }
            }
        }
    }

    fun searchCities(cityName: String): LiveData<OpenCageResult?> {
        job = Job()
        return object : LiveData<OpenCageResult?>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val response = RetrofitClient.openCageService.searchCities(cityName, openCageKey, "fa")
                        withContext(Main) {
                            if (response.isSuccessful) {
                                value = response.body()
                                theJob.complete()
                            } else {
                                value = null
                                theJob.cancel()
                            }
                        }
                    }
                }
            }
        }
    }


    fun cancelJobs() {
        job?.cancel()
    }
}