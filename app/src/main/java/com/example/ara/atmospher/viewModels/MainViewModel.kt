package com.example.ara.atmospher.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ara.atmospher.model.WeatherData
import com.example.ara.atmospher.repository.Repository

class MainViewModel : ViewModel() {
    private val _cityName: MutableLiveData<String> = MutableLiveData()

    // whenever `_cityName` changed run the `getWeather` from Repository
    val weatherDate: LiveData<WeatherData?> = Transformations
            .switchMap(_cityName) { cityName ->
                Repository.getWeather(cityName)
            }

    fun setCityName(cityName: String) {
        if (_cityName.value == cityName)
            return

        _cityName.value = cityName
    }

    fun cancelJobs() {
        Repository.cancelJobs()
    }
}