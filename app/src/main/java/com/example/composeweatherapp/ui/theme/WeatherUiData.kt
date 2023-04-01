package com.example.composeweatherapp.ui.theme

import com.example.composeweatherapp.R

sealed class WeatherType(
    val weatherDesc: String,
    val iconRes: Duel
) {
    object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = Duel(R.drawable.sunny,R.drawable.moon),
    )
    object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = Duel(R.drawable.sunny,R.drawable.moon)
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = Duel(R.drawable.sunny,R.drawable.moon)
    )
    object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconRes = Duel(R.drawable.sunny,R.drawable.moon)
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
        iconRes =Duel( R.drawable.ic_very_cloudy, R.drawable.ic_very_cloudy)
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        iconRes =Duel( R.drawable.ic_very_cloudy, R.drawable.ic_very_cloudy)
    )
    object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
        iconRes = Duel(R.drawable.ic_rainshower,R.drawable.ic_rainshower)
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate drizzle",
        iconRes = Duel(R.drawable.ic_rainshower,R.drawable.ic_rainshower)
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = "Dense drizzle",
        iconRes = Duel(R.drawable.ic_rainshower,R.drawable.ic_rainshower)
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight freezing drizzle",
        iconRes = Duel(R.drawable.ic_snowyrainy,R.drawable.ic_snowyrainy)
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense freezing drizzle",
        iconRes = Duel(R.drawable.ic_snowyrainy,R.drawable.ic_snowyrainy)
    )
    object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        iconRes = Duel( R.drawable.ic_rainy, R.drawable.ic_rainy)
    )
    object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = Duel( R.drawable.ic_rainy, R.drawable.ic_rainy)
    )
    object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        iconRes = Duel( R.drawable.ic_rainy, R.drawable.ic_rainy)
    )
    object HeavyFreezingRain: WeatherType(
        weatherDesc = "Heavy freezing rain",
        iconRes = Duel(R.drawable.ic_snowyrainy,R.drawable.ic_snowyrainy)
    )
    object SlightSnowFall: WeatherType(
        weatherDesc = "Slight snow fall",
        iconRes = Duel(R.drawable.ic_snowy,R.drawable.ic_snowy)
    )
    object ModerateSnowFall: WeatherType(
        weatherDesc = "Moderate snow fall",
        iconRes = Duel(R.drawable.ic_heavysnow,R.drawable.ic_heavysnow)
    )
    object HeavySnowFall: WeatherType(
        weatherDesc = "Heavy snow fall",
        iconRes = Duel(R.drawable.ic_heavysnow,R.drawable.ic_heavysnow)
    )
    object SnowGrains: WeatherType(
        weatherDesc = "Snow grains",
        iconRes = Duel(R.drawable.ic_heavysnow,R.drawable.ic_heavysnow)
    )
    object SlightRainShowers: WeatherType(
        weatherDesc = "Slight rain showers",
        iconRes = Duel( R.drawable.ic_rainy, R.drawable.ic_rainy)
    )
    object ModerateRainShowers: WeatherType(
        weatherDesc = "Moderate rain showers",
        iconRes = Duel(R.drawable.ic_rainshower,R.drawable.ic_rainshower)
    )
    object ViolentRainShowers: WeatherType(
        weatherDesc = "Violent rain showers",
        iconRes = Duel(R.drawable.ic_rainshower,R.drawable.ic_rainshower)
    )
    object SlightSnowShowers: WeatherType(
        weatherDesc = "Light snow showers",
        iconRes = Duel( R.drawable.ic_snowy, R.drawable.ic_snowy)
    )
    object HeavySnowShowers: WeatherType(
        weatherDesc = "Heavy snow showers",
        iconRes = Duel( R.drawable.ic_snowy, R.drawable.ic_snowy)
    )
    object ModerateThunderstorm: WeatherType(
        weatherDesc = "Moderate thunderstorm",
        iconRes = Duel(R.drawable.ic_thunder,R.drawable.ic_thunder)
    )
    object SlightHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        iconRes = Duel(R.drawable.ic_rainythunder,R.drawable.ic_rainythunder)
    )
    object HeavyHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        iconRes = Duel(R.drawable.ic_rainythunder,R.drawable.ic_rainythunder)
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}

data class Duel(
    val day : Int,
    val night : Int
)

