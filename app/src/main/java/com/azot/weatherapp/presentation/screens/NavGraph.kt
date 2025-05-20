package com.azot.weatherapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "weather_list") {
        composable("weather_list") {
            WeatherListScreen(
                onCityClick = { city ->
                    navController.navigate("weather_detail/$city")
                }
            )
        }
        composable("weather_detail/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            WeatherDetailScreen(city = city)
        }
        composable("weather") {
            WeatherScreen()
        }
    }
}