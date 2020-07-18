package com.example.ara.atmospher.repository

import androidx.lifecycle.LiveData
import com.example.ara.atmospher.model.WeatherData
import com.example.ara.atmospher.retrofit.RetrofitClient
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    var job: CompletableJob? = null
    private const val API_KEY = "ece8f3c084bf15aef779da23422b4aab"

    fun getWeather(cityName: String): LiveData<WeatherData> {
        job = Job()
        return object : LiveData<WeatherData>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val weatherData = RetrofitClient.apiService.getWeather(cityName, API_KEY)
                        withContext(Main) {
                            value = weatherData
                            theJob.complete()
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