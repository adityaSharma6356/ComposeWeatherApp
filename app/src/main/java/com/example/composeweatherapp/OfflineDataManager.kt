package com.example.composeweatherapp

import android.content.Context
import com.example.composeweatherapp.newWeatherData.WeatherDataClasses
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun storedata (it : WeatherDataClasses, context: Context) {
    val sharedpef = context.getSharedPreferences("weatherAppComposeFolder" , Context.MODE_PRIVATE)
    val editor = sharedpef.edit()
    val gson = Gson()
    val json = gson.toJson(it)
    editor.putString("weatherAppComposeFile" , json)
    editor.apply()
}
fun dataLoader(context: Context, data : WeatherDataClasses) : WeatherDataClasses {
    val shared = context.getSharedPreferences("weatherAppComposeFolder" , Context.MODE_PRIVATE)

    val json = shared.getString("weatherAppComposeFile" , null)

    if(json!=null) {
        val turnsType = object : TypeToken<WeatherDataClasses>() {}.type
        return Gson().fromJson(json, turnsType)
    }
    return data
}
fun loadStoredLocation(context: Context) : LocationDetails {
    val shared = context.getSharedPreferences("storedLocationDetails" , Context.MODE_PRIVATE)

    val json = shared.getString("StoredLocation" , null)

    if(json!=null) {
        val turnsType = object : TypeToken<LocationDetails>() {}.type
        return Gson().fromJson(json, turnsType)
    }
    return LocationDetails(28.6 , 77.0)
}

fun storeLocation (it : LocationDetails, context: Context) {
    val sharedpef = context.getSharedPreferences("storedLocationDetails" , Context.MODE_PRIVATE)
    val editor = sharedpef.edit()
    val gson = Gson()
    val json = gson.toJson(it)
    editor.putString("StoredLocation" , json)
    editor.apply()
}
