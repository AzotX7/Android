package com.azot.weatherapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherDetailScreen(
    city: String,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    var forecastList by remember { mutableStateOf<List<WeatherInfo>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(city) {
        try {
            isLoading = true
            forecastList = viewModel.getFiveDayForecast(city)
        } catch (e: Exception) {
            error = "Не удалось загрузить прогноз для $city"
        } finally {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
            }
            forecastList.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(forecastList) { weatherInfo ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(text = "Дата: ${weatherInfo.dateTime}", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Температура: ${weatherInfo.temperature}°K")
                            Text(text = "Влажность: ${weatherInfo.humidity}%")
                            Text(text = "Описание: ${weatherInfo.description}")
                        }
                    }
                }
            }
        }
    }
}
