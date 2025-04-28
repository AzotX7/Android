package com.azot.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    @SerializedName("list") val forecastList: List<ForecastItemDto>
)

data class ForecastItemDto(
    @SerializedName("dt_txt") val dateTimeText: String,
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherDescriptionDto>,
)