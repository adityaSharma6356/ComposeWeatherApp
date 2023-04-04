package com.example.composeweatherapp.newWeatherData

data class WeatherDataClasses(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)