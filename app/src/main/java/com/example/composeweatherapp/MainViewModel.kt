package com.example.composeweatherapp


import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeweatherapp.WeatherData.WeatherApiData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import java.time.LocalTime


class MainViewModel() : ViewModel() {
    val myLocation = LocationDetails(37.4219983, -122.084)
    var state = mutableStateOf(WeatherState())
    var backgroundImageState by mutableStateOf(R.drawable.sunny_background)
    var backgroundImageBlurredState by mutableStateOf(R.drawable.sunny_background_blur)
    var currentDateTime = 0
    var currentDay = 0
    var currentMonth = 0
    var currentYear = 0
    var currentDayName = ""
    var progressState by mutableStateOf(false)
    var barHeightState by mutableStateOf(1000)
    var titleVisibility by mutableStateOf(false)
    var blurImgState by mutableStateOf(650)
    var tempAnimState by mutableStateOf(200)
    var dayDateleftpad by mutableStateOf(50)
    var tempsizestate by mutableStateOf(100)
    var selectedIndex by mutableStateOf(currentDateTime)
    var hourWiseStates = mutableStateListOf(false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,false ,)
    fun changeBarHeight(){
        barHeightState = if(barHeightState==1000){
            220
        } else {
            1000
        }
        blurImgState = if(blurImgState==0){
            780
        } else {
            0
        }
        tempAnimState = if(tempAnimState==80){
            200
        } else {
            80
        }
        dayDateleftpad = if(dayDateleftpad==50){
            110
        } else {
            50
        }
        tempsizestate = if(tempsizestate==100){
            70
        } else {
            100
        }
    }
    private fun setBackgroundImage() {
        backgroundImageState = when(state.value.weatherInfo?.hourly?.weathercode?.get(currentDateTime)){
            0,1,2,3,45,48 ->{
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
        backgroundImageBlurredState = when(state.value.weatherInfo?.hourly?.weathercode?.get(currentDateTime)){
            0,1,2,3,45,48 ->{
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

    suspend fun loadWeatherInfo(context: Context) {
        progressState = true
        viewModelScope.launch {
            state.value.isLoading = true
            state.value.error = null
            val userData  = try {
                API_Instance.api.getWeatherData(myLocation.latitiude.toFloat(), myLocation.longitude.toFloat())
            } catch (e: IOException) {
                state.value.weatherInfo = null
                state.value.isLoading = false
                state.value.error = "IOException , You might not have Internet Connection"
                Log.e("MainActivity" , "IOException , You might not have Internet Connection")
                progressState = false
                return@launch
            } catch (e: HttpException) {
                state.value.weatherInfo = null
                state.value.isLoading = false
                state.value.error = "HttpException  , unexpected response"
                Log.e("MainActivity" , "HttpException , unexpected response")
                progressState = false
                return@launch
            }
            if(userData.isSuccessful && userData.body() != null) {
                state.value.weatherInfo = userData.body()
                state.value.isLoading = false
                state.value.error = null
                storedata(userData.body()!!, context)
                setBackgroundImage()
                Log.d("MainActivity" , "Response Successful")
//                Log.d("locationtag", userData.body().toString(), )
                progressState = false
            } else {
                state.value.weatherInfo = null
                state.value.isLoading = false
                state.value.error = null
                Log.e("MainActivity" , "Response not successful")
                progressState = false
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

fun storedata (it : WeatherApiData, context: Context) {
    val sharedpef = context.getSharedPreferences("weatherAppComposeFolder" , Context.MODE_PRIVATE)
    val editor = sharedpef.edit()
    val gson = Gson()
    val json = gson.toJson(it)
    editor.putString("weatherAppComposeFile" , json)
    editor.apply()
}
fun dataLoader(context: Context) : WeatherApiData? {
    val shared = context.getSharedPreferences("weatherAppComposeFolder" , Context.MODE_PRIVATE)

    val json = shared.getString("weatherAppComposeFile" , null)

    if(json!=null) {
        val turnsType = object : TypeToken<WeatherApiData>() {}.type
        return Gson().fromJson(json, turnsType)
    }
    return null
}