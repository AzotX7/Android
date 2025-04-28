package com.azot.weatherapp.domain.usecase

import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): WeatherInfo {
        return repository.getWeatherByCity(city)
    }
}