package com.azot.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("name") val cityName: String,
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherDescriptionDto>
)

data class MainDto(
    @SerializedName("temp") val temp: Float,
    @SerializedName("humidity") val humidity: Int
)

data class WeatherDescriptionDto(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)