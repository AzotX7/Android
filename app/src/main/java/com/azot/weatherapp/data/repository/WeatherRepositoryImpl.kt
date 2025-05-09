package com.azot.weatherapp.data.repository

import android.content.Context
import android.widget.Toast
import com.azot.weatherapp.data.cache.InMemoryWeatherCache
import com.azot.weatherapp.data.remote.WeatherApiService
import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.domain.repository.DataSource
import com.azot.weatherapp.domain.repository.WeatherRepository
import com.azot.weatherapp.domain.repository.WeatherResult
import com.azot.weatherapp.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): WeatherResult {
        InMemoryWeatherCache.get(city)?.let {
            return WeatherResult(it, DataSource.CACHE)
        }

        val response = apiService.getCurrentWeather(city, Constants.API_KEY)
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

        val weatherInfo = WeatherInfo(
            cityName = response.cityName,
            temperature = response.main.temp,
            humidity = response.main.humidity,
            description = response.weather.firstOrNull()?.description.orEmpty(),
            icon = response.weather.firstOrNull()?.icon.orEmpty(),
            dateTime = currentDateTime
        )

        InMemoryWeatherCache.put(city, weatherInfo)
        return WeatherResult(weatherInfo, DataSource.API)
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