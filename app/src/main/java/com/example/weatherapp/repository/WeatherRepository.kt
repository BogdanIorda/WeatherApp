package com.example.weatherapp.repository

import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherObject
import com.example.weatherapp.network.WeatherApi
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(
        cityQuery: String,
        units: String
    ): DataOrException<WeatherObject, Boolean, Exception> {

        val response = try {
            api.getWeather(query = cityQuery, units = units)

        } catch (exception: Exception) {
            return DataOrException(e = exception)

        }
        return DataOrException(data = response)
    }
}