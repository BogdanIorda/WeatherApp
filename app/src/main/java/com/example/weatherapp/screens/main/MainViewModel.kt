package com.example.weatherapp.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherObject
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private var activeSearchJob: Job? = null


    val data: MutableState<DataOrException<WeatherObject, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )

    fun getWeather(city: String, units: String) {
        viewModelScope.launch {

            activeSearchJob?.cancel()

            activeSearchJob = viewModelScope.launch {

                if (city.isEmpty()) return@launch

                data.value = DataOrException(loading = true, e = Exception(""))

                val response = repository.getWeather(cityQuery = city, units = units)

                response.loading = false

                data.value = response
            }
        }
    }
}








