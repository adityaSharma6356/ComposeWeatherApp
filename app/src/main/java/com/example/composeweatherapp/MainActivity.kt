package com.example.composeweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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


var weathercode : WeatherDataClasses? = null
class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var locationCallback: LocationCallback? = null
    private var locationRequired = false
    private var fusedLocationClient: FusedLocationProviderClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        weathercode = Gson().fromJson(jsonstring, WeatherDataClasses::class.java)
        val mainViewModel : MainViewModel by viewModels()
        mainViewModel.state!!.weatherInfo =  dataLoader(this, weathercode!!)
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
                startLocationUpdates()
                mainViewModel.loadWeatherInfo(this@MainActivity)
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
            var currentLocation by remember { mutableStateOf(LocationDetails(0.0, 0.0)) }
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    for (lo in p0.locations) {
                        currentLocation = LocationDetails(lo.latitude, lo.longitude)
                        mainViewModel.myLocation.longitude = currentLocation.longitude
                        mainViewModel.myLocation.latitiude = currentLocation.latitiude
                        lifecycleScope.launch { mainViewModel.loadWeatherInfo(this@MainActivity) }
                    }
                }
            }
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
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 500000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }
    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }
}


