package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_table")
data class Favorite(

    @PrimaryKey
    @ColumnInfo(name = "city_name")
    val city: String,

    @ColumnInfo(name = "country_name")
    val country: String,
)
