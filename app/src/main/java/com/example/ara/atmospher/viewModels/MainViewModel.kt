package com.example.ara.atmospher.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ara.atmospher.models.openWeather.oneCall.OneCall
import com.example.ara.atmospher.models.opencage.Geometry
import com.example.ara.atmospher.models.opencage.OpenCageResult
import com.example.ara.atmospher.repository.Repository

class MainViewModel : ViewModel() {
    private val _cityName: MutableLiveData<String> = MutableLiveData()
    private val _geometry: MutableLiveData<Geometry> = MutableLiveData()

    // whenever `_cityName` changed run the `getWeather` from Repository
    val oneCallData: LiveData<OneCall?> = Transformations
            .switchMap(_geometry) { geometry ->
                Repository.oneCall(geometry.lat, geometry.lng)
            }

    val citiesData: LiveData<OpenCageResult?> = Transformations
            .switchMap(_cityName) {
                Repository.searchCities(it)
            }

    fun setCityName(cityName: String) {
        if (_cityName.value == cityName)
            return

        _cityName.value = cityName
    }

    fun setCityGeometry(geometry: Geometry) {
        if (_geometry.value == geometry)
            return

        _geometry.value = geometry
    }

    fun cancelJobs() {
        Repository.cancelJobs()
    }
}