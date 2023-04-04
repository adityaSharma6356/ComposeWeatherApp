package com.example.composeweatherapp

import com.example.composeweatherapp.newWeatherData.WeatherDataClasses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Provider {
    var possible : Boolean
    @GET("v1/forecast.json?key=1545a65ef4d54b1a84b161753230404&days=7&aqi=yes&alerts=yes")
    suspend fun getWeatherData(
        @Query("q") lat: String
    ): Response<WeatherDataClasses>

}
