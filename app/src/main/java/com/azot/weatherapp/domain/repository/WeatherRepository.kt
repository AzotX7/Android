package com.azot.weatherapp.domain.repository

import com.azot.weatherapp.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherByCity(city: String): WeatherInfo

    suspend fun getFiveDayForecastByCity(city: String): List<WeatherInfo>
}