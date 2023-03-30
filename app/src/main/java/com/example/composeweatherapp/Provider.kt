package com.example.composeweatherapp

import com.example.composeweatherapp.WeatherData.WeatherApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Provider {
    var possible : Boolean
    @GET("v1/forecast?hourly=temperature_2m&hourly=weathercode&forecast_days=1")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): Response<WeatherApiData>

}
