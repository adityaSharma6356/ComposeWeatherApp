package com.example.composeweatherapp



import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeweatherapp.newWeatherData.WeatherDataClasses
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class MainViewModel : ViewModel() {
    var myLocation = LocationDetails(28.6983, 77.0)
    var state by mutableStateOf(weathercode?.let { WeatherState(weatherInfo = it) })
    var backgroundImageState by mutableStateOf(R.drawable.sunny_background)
    var backgroundImageBlurredState by mutableStateOf(R.drawable.sunny_background_blur)
    var currentDateTime by mutableStateOf(0)
    var currentDay by mutableStateOf(0)
    var currentMonth by mutableStateOf(0)
    var currentYear by mutableStateOf(0)
    var currentDayName by mutableStateOf(" ")
    var progressState by mutableStateOf(false)
    var backHandler by mutableStateOf(false)
    var weathericoncode by mutableStateOf(1000)
    var currentTemp by mutableStateOf(20)
    var currentAQI by mutableStateOf(20)
    var currentLocationName by mutableStateOf("New Delhi")
    var forcastsize = 24
    var currentUIState by mutableStateOf(false)

   fun changeBarHeight(){
       currentUIState = !currentUIState
       backHandler = !backHandler
    }
    fun dayComment() : String{
        return when(state!!.weatherInfo.forecast.forecastday[0].day.avgtemp_c) {
            in(22.0..32.0) -> "Warm day Ahead"
            in(32.0..52.0) -> "Extra warm day ahead"
            in(12.0..22.0) -> "Expect a Cool day ahead"
            in(0.0..12.0) -> "Expect a Cold day ahead"
            else -> ""
        }
    }
    fun feelslike() : String{
        return state!!.weatherInfo.forecast.forecastday[0].day.mintemp_c.toInt().toString() +"°C ~ "+ state!!.weatherInfo.forecast.forecastday[0].day.maxtemp_c.toInt().toString() +"°C   \nFeels like "+ state!!.weatherInfo.current.feelslike_c.toInt().toString() +"°C"
    }
    private fun setBackgroundImage() {
        backgroundImageState = when(state!!.weatherInfo.current.condition.code){
            1000,1003,1006,1009,1030 ->{
                if(currentDateTime in 7..19)
                    R.drawable.sunny_background
                else
                    R.drawable.night_background
            }
            else ->{
                if(currentDateTime in 7..19)
                    R.drawable.rainy_day_background
                else
                    R.drawable.rainy_night_background
            }
        }
        backgroundImageBlurredState = when(state!!.weatherInfo.current.condition.code){
            1000,1003,1006,1009, 1030 ->{
                if(currentDateTime in 7..19)
                    R.drawable.sunny_background_blur
                else
                    R.drawable.night_background_blur
            }
            else ->{
                if(currentDateTime in 7..19)
                    R.drawable.rainy_day_background_blur
                else
                    R.drawable.rainy_night_background_blur
            }
        }
    }

    fun loadWeatherInfo(context: Context) {
        progressState = true
        viewModelScope.launch {
            state!!.isLoading = true
            state!!.error = null
            val userData  = try {
                API_Instance.api.getWeatherData("${myLocation.latitiude},${myLocation.longitude}")
            } catch (e: IOException) {
                state!!.weatherInfo = dataLoader(context, weathercode!!)
                state!!.isLoading = false
                state!!.error = "IOException , You might not have Internet Connection"
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity" , "IOException , You might not have Internet Connection")
                weathericoncode = state!!.weatherInfo.current.condition.code
                currentTemp = state!!.weatherInfo.current.temp_c.toInt()
                currentAQI = state!!.weatherInfo.forecast.forecastday[0].day.air_quality.pm10.toInt()
                forcastsize = state!!.weatherInfo.forecast.forecastday[0].hour.size
                currentLocationName =  state!!.weatherInfo.location.name
                progressState = false
                return@launch
            } catch (e: HttpException) {
                state!!.isLoading = false
                state!!.error = "HttpException  , unexpected response"
                Log.e("MainActivity" , "HttpException , unexpected response")
                progressState = false
                return@launch
            }
            if(userData.isSuccessful && userData.body() != null) {
                state!!.weatherInfo = userData.body()!!
                state!!.isLoading = false
                state!!.error = null
                storedata(userData.body()!!, context)
                setBackgroundImage()
                Log.d("MainActivity" , "Response Successful")
                Log.d("locationtag", state!!.weatherInfo.toString())
                progressState = false
                weathericoncode = state!!.weatherInfo.current.condition.code
                currentTemp = state!!.weatherInfo.current.temp_c.toInt()
                currentAQI = state!!.weatherInfo.forecast.forecastday[0].day.air_quality.pm10.toInt()
                forcastsize = state!!.weatherInfo.forecast.forecastday[0].hour.size
                currentLocationName =  state!!.weatherInfo.location.region
            } else {
                state!!.isLoading = false
                state!!.error = null
                Log.e("MainActivity" , "Response not successful")
                progressState = false
            }
        }
    }
    fun imagecode(it : Int?, index : Int): Int{
        return when(it) {
            1000 -> if (index in 7..19) R.drawable.ic_sunny else R.drawable.ic_moon
            1003 -> R.drawable.partly_cloudy
            1006 -> R.drawable.cloudy
            1009 -> R.drawable.cloudy
            1030 -> R.drawable.fog
            1063 -> R.drawable.ic_rainshower
            1087, 1273, 1276, 1279 -> R.drawable.ic_thunder
            1117 -> R.drawable.ic_heavysnow
            1135 -> R.drawable.fog
            1158,1153,1168,1171,1183,1180,1186,1189,1246,1249 -> R.drawable.ic_rainshower
            1192,1195 -> R.drawable.ic_rainy
            1198 -> R.drawable.ic_snowyrainy
            else -> R.drawable.ic_heavysnow
        }
    }
}

data class WeatherState(
    var weatherInfo: WeatherDataClasses,
    var isLoading: Boolean = false,
    var error: String? = null
)
data class LocationDetails(
    var latitiude : Double,
    var longitude : Double,
)

