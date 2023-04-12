package com.example.composeweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.composeweatherapp.newWeatherData.WeatherDataClasses
import com.example.composeweatherapp.newWeatherData.jsonstring
import com.example.composeweatherapp.ui.Composables.MainUI
import com.example.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.*
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.Executor
import java.util.function.Consumer


var weathercode : WeatherDataClasses? = null
class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        weathercode = Gson().fromJson(jsonstring, WeatherDataClasses::class.java)
        val mainViewModel : MainViewModel by viewModels()
        mainViewModel.state!!.weatherInfo =  dataLoader(this, weathercode!!)
        mainViewModel.myLocation = loadStoredLocation(this)
        val ct = Instant.now()
        mainViewModel.currentDateTime = ct.atZone(ZoneId.systemDefault()).hour
        mainViewModel.currentDay = ct.atZone(ZoneId.systemDefault()).dayOfMonth
        mainViewModel.currentMonth = ct.atZone(ZoneId.systemDefault()).monthValue
        mainViewModel.currentYear = ct.atZone(ZoneId.systemDefault()).year
        mainViewModel.currentDayName = ct.atZone(ZoneId.systemDefault()).dayOfWeek.name

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            lifecycleScope.launch {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        if(location!=null){
                            mainViewModel.myLocation.longitude = location.longitude
                            mainViewModel.myLocation.latitiude = location.latitude
                            storeLocation(LocationDetails(location.latitude , location.longitude), this@MainActivity)
                        }
                        mainViewModel.loadWeatherInfo(this@MainActivity)
                    }
            }
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
        setContent {
            ComposeWeatherAppTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    val window = (this as Activity).window
                    window.statusBarColor = Color.Transparent.toArgb()
                    window.navigationBarColor = Color.Transparent.toArgb()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        window.isNavigationBarContrastEnforced = false
                    }
                    systemUiController.statusBarDarkContentEnabled = false
                }
                MainUI(mainViewModel = mainViewModel, context = this)
            }
        }
    }
}


