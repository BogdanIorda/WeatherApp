package com.example.weatherapp.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Unit
import com.example.weatherapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: WeatherDbRepository) :
    ViewModel() {

    private val _unitsList = MutableStateFlow<List<Unit>>(emptyList())

    val unitsList = _unitsList.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getAllUnits().distinctUntilChanged().collect { listOfUnits ->
                _unitsList.value = listOfUnits
            }
        }
    }


    fun addUnit(unit: Unit) =
        viewModelScope.launch { settingsRepository.insertUnit(unit) }

    fun updateUnit(unit: Unit) =
        viewModelScope.launch { settingsRepository.updateUnit(unit) }

    fun removeUnit(unit: Unit) =
        viewModelScope.launch { settingsRepository.deleteUnit(unit) }

    fun removeAllUnites() = viewModelScope.launch { settingsRepository.deleteAllUnites() }

}
