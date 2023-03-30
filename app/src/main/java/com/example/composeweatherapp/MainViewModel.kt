package com.example.composeweatherapp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeweatherapp.WeatherData.WeatherApiData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel() : ViewModel() {
    val myLocation = LocationDetails(37.4219983, -122.084)
    var state = mutableStateOf(WeatherState())

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state.value.isLoading = true
            state.value.error = null
            val userData  = try {
                API_Instance.api.getWeatherData(myLocation.latitiude, myLocation.longitude)
            } catch (e: IOException) {
                state.value.weatherInfo = null
                state.value.isLoading = false
                state.value.error = "IOException , You might not have Internet Connection"
                Log.e("MainActivity" , "IOException , You might not have Internet Connection")
                return@launch
            } catch (e: HttpException) {
                state.value.weatherInfo = null
                state.value.isLoading = false
                state.value.error = "HttpException  , unexpected response"
                Log.e("MainActivity" , "HttpException , unexpected response")
                return@launch
            }
            if(userData.isSuccessful && userData.body() != null) {
                state.value.weatherInfo = userData.body()
                state.value.isLoading = false
                state.value.error = null
            } else {
                state.value.weatherInfo = null
                state.value.isLoading = false
                state.value.error = null
                Log.e("MainActivity" , "Response not successful")
            }
        }
    }
}

data class WeatherState(
    var weatherInfo: WeatherApiData? = null,
    var isLoading: Boolean = false,
    var error: String? = null
)
data class LocationDetails(
    var latitiude : Double,
    var longitude : Double,
)
