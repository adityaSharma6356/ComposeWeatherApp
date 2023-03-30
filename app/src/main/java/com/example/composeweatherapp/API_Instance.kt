package com.example.composeweatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API_Instance {
    private val API_URL = "https://api.open-meteo.com/"

    val api : Provider by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Provider::class.java)
    }
}