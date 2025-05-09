package com.azot.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azot.weatherapp.data.cache.InMemoryWeatherCache
import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.domain.repository.DataSource
import com.azot.weatherapp.domain.repository.WeatherResult
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

    init {
        viewModelScope.launch {
            InMemoryWeatherCache.clear()
        }
    }

    private val _weatherList = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherList: StateFlow<List<WeatherInfo>> = _weatherList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    fun loadWeatherForCities(cities: List<String>) {
        viewModelScope.launch {
            val resultList = mutableListOf<WeatherInfo>()
            for (city in cities) {
                try {
                    val result = getWeatherByCityUseCase(city)
                    resultList.add(result.data)
                } catch (e: Exception) {
                    _errorMessage.value = "Ошибка загрузки данных для города: $city"
                }
            }
            _weatherList.value = resultList
        }
    }

    fun loadCityWithToast(city: String) {
        viewModelScope.launch {
            try {
                val result = getWeatherByCityUseCase(city)
                _toastMessage.value = when (result.source) {
                    DataSource.CACHE -> "Данные из кэша"
                    DataSource.API -> "Данные из API"
                }

                _weatherList.value = _weatherList.value.map {
                    if (it.cityName == city) result.data else it
                }
            } catch (e: Exception) {
                _toastMessage.value = "Ошибка загрузки данных"
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }



    suspend fun getWeatherForCity(city: String): WeatherResult {
        return getWeatherByCityUseCase(city)
    }

    suspend fun getFiveDayForecast(city: String): List<WeatherInfo> {
        return getFiveDayForecastUseCase(city)
    }
}