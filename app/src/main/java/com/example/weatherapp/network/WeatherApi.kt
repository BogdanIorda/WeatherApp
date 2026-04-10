package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherObject
import com.example.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

//data/2.5/forecast/daily
//appid=ed60fcfbd110ee65c7150605ea8aceea&units=imperial

//https://api.openweathermap.org/data/2.5/forecast/daily?q=austin&appid=ed60fcfbd110ee65c7150605ea8aceea&units=imperial
@Singleton
interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("appId") appId: String = Constants.API_KEY,
        @Query("units") units: String = "imperial"
    ): WeatherObject
}