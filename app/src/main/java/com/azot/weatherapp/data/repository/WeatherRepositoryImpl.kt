package com.azot.weatherapp.data.repository

import com.azot.weatherapp.data.remote.WeatherApiService
import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.domain.repository.WeatherRepository
import com.azot.weatherapp.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): WeatherInfo {
        val response = apiService.getCurrentWeather(city, Constants.API_KEY)

        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())


        return WeatherInfo(
            cityName = response.cityName,
            temperature = response.main.temp,
            humidity = response.main.humidity,
            description = response.weather.firstOrNull()?.description.orEmpty(),
            icon = response.weather.firstOrNull()?.icon.orEmpty(),
            dateTime = currentDateTime
        )
    }

    override suspend fun getFiveDayForecastByCity(city: String): List<WeatherInfo> {
        val response = apiService.getFiveDayForecast(city, Constants.API_KEY)

        return response.forecastList.map { item ->
            WeatherInfo(
                cityName = city,
                temperature = item.main.temp,
                humidity = item.main.humidity,
                description = item.weather.firstOrNull()?.description.orEmpty(),
                icon = item.weather.firstOrNull()?.icon.orEmpty(),
                dateTime = item.dateTimeText
            )
        }
    }
}