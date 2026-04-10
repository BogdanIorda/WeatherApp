package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {

    //Favorites table
    @Query("SELECT * from favorite_table")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from favorite_table where city_name =:city")
    suspend fun getFavoriteByName(city: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(favorite: Favorite)

    @Query("DELETE from favorite_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(favorite: Favorite)

    //Unit table
    @Query("SELECT * from settings_table")
    fun getAllUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(unit: Unit)

    @Delete
    suspend fun delete(unit: Unit)

    @Query("DELETE from settings_table")
    suspend fun deleteAllUnits()
}