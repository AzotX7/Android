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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azot.weatherapp.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherDetailScreen(
    city: String,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.forecastState.collectAsStateWithLifecycle()

    LaunchedEffect(city) {
        viewModel.loadFiveDayForecast(city)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> CircularProgressIndicator()

            state.error != null -> Text(
                text = state.error ?: "",
                color = MaterialTheme.colorScheme.error
            )

            state.forecastList.isNotEmpty() -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.forecastList) { weatherInfo ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text("Дата: ${weatherInfo.dateTime}", style = MaterialTheme.typography.titleMedium)
                        Text("Температура: ${weatherInfo.temperature}°K")
                        Text("Влажность: ${weatherInfo.humidity}%")
                        Text("Описание: ${weatherInfo.description}")
                    }
                }
            }
        }
    }
}
