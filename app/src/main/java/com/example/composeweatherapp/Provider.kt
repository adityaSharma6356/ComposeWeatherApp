package com.example.composeweatherapp

import com.example.composeweatherapp.NewWeatherData.WeatherDataClasses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Provider {
    var possible : Boolean
    @GET("v1/forecast.json?key=95f1ce3bc2614f7b8bc164711232903&days=7&aqi=yes&alerts=yes")
    suspend fun getWeatherData(
        @Query("q") lat: String
    ): Response<WeatherDataClasses>

}
