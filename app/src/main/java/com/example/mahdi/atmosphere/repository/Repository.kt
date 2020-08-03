package com.example.mahdi.atmosphere.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mahdi.atmosphere.models.openWeather.oneCall.OneCall
import com.example.mahdi.atmosphere.models.opencage.OpenCageResult
import com.example.mahdi.atmosphere.retrofit.RetrofitClient
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.io.IOException

object Repository {

    var job: CompletableJob? = null
    private const val openWeatherKey = "ece8f3c084bf15aef779da23422b4aab"
    private const val openCageKey = "5f484667ba264bedbef60ff442b75270"

    fun oneCall(lat: Double, lon: Double): LiveData<OneCall?> {
        job = Job()
        return object : LiveData<OneCall?>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        try {
                            val response = RetrofitClient.openWeatherService.oneCall(lat, lon, openWeatherKey)
                            if (response != null && response.isSuccessful) {
                                withContext(Main) {
                                    value = response.body()
                                    theJob.complete()
                                }
                            } else {
                                throw IOException("unable to connect")
                            }
                        } catch (error: IOException) {
                            withContext(Main){
                                value = null
                            }
                            job?.cancelAndJoin()
                            Log.e("NETWORK", "onActive: $error")
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
                        try {
                            val response = RetrofitClient.openCageService.searchCities(cityName, openCageKey, "fa")
                            if (response != null && response.isSuccessful) {
                                withContext(Main) {
                                    value = response.body()
                                    theJob.complete()
                                }
                            } else {
                                throw IOException("unable to connect")
                            }
                        } catch (error: IOException) {
                            withContext(Main){
                                value = null
                            }
                            job?.cancel()
                            Log.e("NETWORK", "onActive: $error")
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