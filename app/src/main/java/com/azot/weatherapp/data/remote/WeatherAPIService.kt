package com.azot.weatherapp.data.remote

import com.azot.weatherapp.data.remote.dto.ForecastResponseDto
import com.azot.weatherapp.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherResponseDto


    @GET("forecast")
    suspend fun getFiveDayForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): ForecastResponseDto
}
