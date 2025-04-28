package com.azot.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.domain.usecase.GetFiveDayForecastUseCase
import com.azot.weatherapp.domain.usecase.GetWeatherByCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val getFiveDayForecastUseCase: GetFiveDayForecastUseCase
) : ViewModel() {

    private val _weatherList = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherList: StateFlow<List<WeatherInfo>> = _weatherList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadWeatherForCities(cities: List<String>) {
        viewModelScope.launch {
            val resultList = mutableListOf<WeatherInfo>()
            for (city in cities) {
                try {
                    val weather = getWeatherByCityUseCase(city)
                    resultList.add(weather)
                } catch (e: Exception) {
                    _errorMessage.value = "Ошибка загрузки данных для города: $city"
                }
            }
            _weatherList.value = resultList
        }
    }


    suspend fun getWeatherForCity(city: String): WeatherInfo {
        return getWeatherByCityUseCase(city)
    }

    suspend fun getFiveDayForecast(city: String): List<WeatherInfo> {
        return getFiveDayForecastUseCase(city)
    }
}