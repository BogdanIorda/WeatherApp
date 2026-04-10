package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getAllFavorites(): Flow<List<Favorite>> =
        weatherDao.getAllFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insert(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.update(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.delete(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAll()

    fun getAllUnits(): Flow<List<Unit>> = weatherDao.getAllUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insert(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.update(unit)
    suspend fun deleteUnit(unit: Unit) = weatherDao.delete(unit)
    suspend fun deleteAllUnites() = weatherDao.deleteAllUnits()


}