package com.azot.weatherapp.data.cache

import com.azot.weatherapp.domain.model.WeatherInfo

object InMemoryWeatherCache {
    private val weatherCache = mutableMapOf<String, WeatherInfo>()
    private val timestampMap = mutableMapOf<String, Long>()
    private val requestHistory = ArrayDeque<String>()

    private const val CACHE_DURATION_MS = 5 * 60 * 1000

    fun get(city: String): WeatherInfo? {
        val now = System.currentTimeMillis()
        val timestamp = timestampMap[city] ?: return null


        return if (now - timestamp < CACHE_DURATION_MS) {
            weatherCache[city]
        } else {
            remove(city)
            null
        }
    }

    fun put(city: String, data: WeatherInfo) {
        val now = System.currentTimeMillis()
        weatherCache[city] = data
        timestampMap[city] = now


        requestHistory.addLast(city)
        if (requestHistory.size > 4) requestHistory.removeFirst()


        val recentCities = requestHistory.toSet()
        if (recentCities.size >= 4 && requestHistory.count { it == city } == 2) {
            clear()
        }
    }

    private fun remove(city: String) {
        weatherCache.remove(city)
        timestampMap.remove(city)
    }

    fun clear() {
        weatherCache.clear()
        timestampMap.clear()
        requestHistory.clear()
    }
}