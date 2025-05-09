package com.azot.weatherapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azot.weatherapp.domain.model.WeatherInfo
import com.azot.weatherapp.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherListScreen(
    onCityClick: (String) -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weatherList by viewModel.weatherList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadWeatherForCities(listOf("Moscow", "London", "New York", "Tokyo", "Paris"))
    }

    if (errorMessage != null) {
        Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(weatherList) { weather ->
            WeatherListItem(weatherInfo = weather, onClick = {
                viewModel.loadCityWithToast(weather.cityName)
                onCityClick(weather.cityName)
            })
        }
    }
}

@Composable
fun WeatherListItem(
    weatherInfo: WeatherInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = weatherInfo.cityName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Температура: ${weatherInfo.temperature}°K")
            Text(text = "Влажность: ${weatherInfo.humidity}%")
        }
    }
}