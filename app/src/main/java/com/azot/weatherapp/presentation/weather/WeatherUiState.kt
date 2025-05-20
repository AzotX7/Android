package com.azot.weatherapp.presentation.weather

import com.azot.weatherapp.domain.repository.WeatherResult

data class WeatherUiState(
    val cityInput: String = "",
    val weatherResult: WeatherResult? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)