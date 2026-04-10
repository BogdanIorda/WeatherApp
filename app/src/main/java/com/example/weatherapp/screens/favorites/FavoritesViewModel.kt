package com.example.weatherapp.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(private val favoritesRepository: WeatherDbRepository) :
    ViewModel() {

    private val _favoritesList = MutableStateFlow<List<Favorite>>(emptyList())

    val favoritesList = _favoritesList.asStateFlow()

    init {
        viewModelScope.launch {// (Dispatchers.IO) if needed
            favoritesRepository.getAllFavorites().distinctUntilChanged()
                .collect { listOfFavorites ->
                    _favoritesList.value = listOfFavorites
                }
        }
    }


    fun addFavorite(favorite: Favorite) =
        viewModelScope.launch { favoritesRepository.insertFavorite(favorite) }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch { favoritesRepository.updateFavorite(favorite) }

    fun removeFavorite(favorite: Favorite) =
        viewModelScope.launch { favoritesRepository.deleteFavorite(favorite) }

    fun removeAllFavorites() = viewModelScope.launch { favoritesRepository.deleteAllFavorites() }

}