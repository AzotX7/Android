package com.azot.weatherapp.domain.model

data class WeatherInfo(
    val cityName: String,
    val temperature: Float,
    val humidity: Int,
    val description: String,
    val icon: String,
    val dateTime: String
)