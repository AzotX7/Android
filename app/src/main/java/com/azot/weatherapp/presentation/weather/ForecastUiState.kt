package com.azot.weatherapp.presentation.weather

import com.azot.weatherapp.domain.model.WeatherInfo

data class ForecastUiState(
    val forecastList: List<WeatherInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)