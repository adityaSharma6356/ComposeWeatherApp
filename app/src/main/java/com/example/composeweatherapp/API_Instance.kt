package com.example.composeweatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API_Instance {
    private const val API_URL = "https://api.weatherapi.com/"

    val api : Provider by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Provider::class.java)
    }
}