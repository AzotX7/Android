package com.azot.weatherapp.domain.repository

import com.azot.weatherapp.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherByCity(city: String): WeatherResult

    suspend fun getFiveDayForecastByCity(city: String): List<WeatherInfo>

}

data class WeatherResult(
    val data: WeatherInfo,
    val source: DataSource
)

enum class DataSource {
    CACHE, API
}

