package com.azot.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.domain.repository.WeatherResult
import com.azot.weatherapp.domain.usecase.GetFiveDayForecastUseCase
import com.azot.weatherapp.domain.usecase.GetWeatherByCityUseCase
import com.azot.weatherapp.presentation.weather.ForecastUiState
import com.azot.weatherapp.presentation.weather.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getFiveDayForecastUseCase: GetFiveDayForecastUseCase,
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherUiState())
    val state: StateFlow<WeatherUiState> = _state

    private val _forecastState = MutableStateFlow(ForecastUiState())
    val forecastState: StateFlow<ForecastUiState> = _forecastState

    private val _weatherList = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherList: StateFlow<List<WeatherInfo>> = _weatherList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    fun loadFiveDayForecast(city: String) {
        viewModelScope.launch {
            _forecastState.value = ForecastUiState(isLoading = true)
            try {
                val forecast = getFiveDayForecastUseCase(city)
                _forecastState.value = ForecastUiState(forecastList = forecast)
            } catch (e: Exception) {
                _forecastState.value = ForecastUiState(error = "Не удалось загрузить прогноз для $city")
            }
        }
    }

    fun onCityInputChanged(newCity: String) {
        _state.update { it.copy(cityInput = newCity) }
    }

    fun fetchWeather() {
        val city = state.value.cityInput.trim()
        if (city.isBlank()) return

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val result = getWeatherByCityUseCase(city)
                _state.update {
                    it.copy(weatherResult = result, isLoading = false, errorMessage = null)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, errorMessage = "Не удалось загрузить погоду: ${e.localizedMessage}")
                }
            }
        }
    }

    fun loadWeatherForCities(cities: List<String>) {
        viewModelScope.launch {
            val results = mutableListOf<WeatherInfo>()
            var lastError: String? = null

            cities.forEach { city ->
                try {
                    val weather = getWeatherByCityUseCase(city).data
                    results.add(weather)
                } catch (e: Exception) {
                    lastError = "Ошибка загрузки данных для $city"
                }
            }

            _weatherList.value = results
            _errorMessage.value = lastError
        }
    }

    fun loadCityWithToast(city: String) {
        _toastMessage.value = "Загружаем данные для $city"
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
