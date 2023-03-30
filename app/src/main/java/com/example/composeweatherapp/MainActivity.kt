package com.example.composeweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var locationCallback: LocationCallback? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequired = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            lifecycleScope.launch {
                startLocationUpdates()
                mainViewModel.loadWeatherInfo()
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
                val context = LocalContext.current
                var currentLocation by remember { mutableStateOf(LocationDetails(0.0, 0.0)) }
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {
                        for (lo in p0.locations) {
                            currentLocation = LocationDetails(lo.latitude, lo.longitude)
                            mainViewModel.myLocation.longitude = currentLocation.longitude
                            mainViewModel.myLocation.latitiude = currentLocation.latitiude
                        }
                    }
                }
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(color = Color.Transparent)
                    systemUiController.systemBarsDarkContentEnabled = true
                }




            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
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


