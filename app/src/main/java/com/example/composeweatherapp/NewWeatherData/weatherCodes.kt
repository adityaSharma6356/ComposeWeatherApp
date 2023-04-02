package com.example.composeweatherapp.NewWeatherData

import com.example.composeweatherapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class WeatherConditionCode(
    val code: Int,
    val day: String,
    val night: String,
    val icon: Int
)

data class codes(
    val weathercodes : ArrayList<WeatherConditionCode>
)
//
//val jsonstring = "[\n" +
//        "  {\n" +
//        "    \"code\": 1000,\n" +
//        "    \"day\": \"Sunny\",\n" +
//        "    \"night\": \"Clear\",\n" +
//        "    \"icon\": 113\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1003,\n" +
//        "    \"day\": \"Partly cloudy\",\n" +
//        "    \"night\": \"Partly cloudy\",\n" +
//        "    \"icon\": 116\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1006,\n" +
//        "    \"day\": \"Cloudy\",\n" +
//        "    \"night\": \"Cloudy\",\n" +
//        "    \"icon\": 119\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1009,\n" +
//        "    \"day\": \"Overcast\",\n" +
//        "    \"night\": \"Overcast\",\n" +
//        "    \"icon\": 122\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1030,\n" +
//        "    \"day\": \"Mist\",\n" +
//        "    \"night\": \"Mist\",\n" +
//        "    \"icon\": 143\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1063,\n" +
//        "    \"day\": \"Patchy rain possible\",\n" +
//        "    \"night\": \"Patchy rain possible\",\n" +
//        "    \"icon\": 176\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1066,\n" +
//        "    \"day\": \"Patchy snow possible\",\n" +
//        "    \"night\": \"Patchy snow possible\",\n" +
//        "    \"icon\": 179\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1069,\n" +
//        "    \"day\": \"Patchy sleet possible\",\n" +
//        "    \"night\": \"Patchy sleet possible\",\n" +
//        "    \"icon\": 182\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1072,\n" +
//        "    \"day\": \"Patchy freezing drizzle possible\",\n" +
//        "    \"night\": \"Patchy freezing drizzle possible\",\n" +
//        "    \"icon\": 185\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1087,\n" +
//        "    \"day\": \"Thundery outbreaks possible\",\n" +
//        "    \"night\": \"Thundery outbreaks possible\",\n" +
//        "    \"icon\": 200\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1114,\n" +
//        "    \"day\": \"Blowing snow\",\n" +
//        "    \"night\": \"Blowing snow\",\n" +
//        "    \"icon\": 227\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1117,\n" +
//        "    \"day\": \"Blizzard\",\n" +
//        "    \"night\": \"Blizzard\",\n" +
//        "    \"icon\": 230\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1135,\n" +
//        "    \"day\": \"Fog\",\n" +
//        "    \"night\": \"Fog\",\n" +
//        "    \"icon\": 248\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1147,\n" +
//        "    \"day\": \"Freezing fog\",\n" +
//        "    \"night\": \"Freezing fog\",\n" +
//        "    \"icon\": 260\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1150,\n" +
//        "    \"day\": \"Patchy light drizzle\",\n" +
//        "    \"night\": \"Patchy light drizzle\",\n" +
//        "    \"icon\": 263\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1153,\n" +
//        "    \"day\": \"Light drizzle\",\n" +
//        "    \"night\": \"Light drizzle\",\n" +
//        "    \"icon\": 266\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1168,\n" +
//        "    \"day\": \"Freezing drizzle\",\n" +
//        "    \"night\": \"Freezing drizzle\",\n" +
//        "    \"icon\": 281\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1171,\n" +
//        "    \"day\": \"Heavy freezing drizzle\",\n" +
//        "    \"night\": \"Heavy freezing drizzle\",\n" +
//        "    \"icon\": 284\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1180,\n" +
//        "    \"day\": \"Patchy light rain\",\n" +
//        "    \"night\": \"Patchy light rain\",\n" +
//        "    \"icon\": 293\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1183,\n" +
//        "    \"day\": \"Light rain\",\n" +
//        "    \"night\": \"Light rain\",\n" +
//        "    \"icon\": 296\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1186,\n" +
//        "    \"day\": \"Moderate rain at times\",\n" +
//        "    \"night\": \"Moderate rain at times\",\n" +
//        "    \"icon\": 299\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1189,\n" +
//        "    \"day\": \"Moderate rain\",\n" +
//        "    \"night\": \"Moderate rain\",\n" +
//        "    \"icon\": 302\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1192,\n" +
//        "    \"day\": \"Heavy rain at times\",\n" +
//        "    \"night\": \"Heavy rain at times\",\n" +
//        "    \"icon\": 305\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1195,\n" +
//        "    \"day\": \"Heavy rain\",\n" +
//        "    \"night\": \"Heavy rain\",\n" +
//        "    \"icon\": 308\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1198,\n" +
//        "    \"day\": \"Light freezing rain\",\n" +
//        "    \"night\": \"Light freezing rain\",\n" +
//        "    \"icon\": 311\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1201,\n" +
//        "    \"day\": \"Moderate or heavy freezing rain\",\n" +
//        "    \"night\": \"Moderate or heavy freezing rain\",\n" +
//        "    \"icon\": 314\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1204,\n" +
//        "    \"day\": \"Light sleet\",\n" +
//        "    \"night\": \"Light sleet\",\n" +
//        "    \"icon\": 317\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1207,\n" +
//        "    \"day\": \"Moderate or heavy sleet\",\n" +
//        "    \"night\": \"Moderate or heavy sleet\",\n" +
//        "    \"icon\": 320\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1210,\n" +
//        "    \"day\": \"Patchy light snow\",\n" +
//        "    \"night\": \"Patchy light snow\",\n" +
//        "    \"icon\": 323\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1213,\n" +
//        "    \"day\": \"Light snow\",\n" +
//        "    \"night\": \"Light snow\",\n" +
//        "    \"icon\": 326\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1216,\n" +
//        "    \"day\": \"Patchy moderate snow\",\n" +
//        "    \"night\": \"Patchy moderate snow\",\n" +
//        "    \"icon\": 329\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1219,\n" +
//        "    \"day\": \"Moderate snow\",\n" +
//        "    \"night\": \"Moderate snow\",\n" +
//        "    \"icon\": 332\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1222,\n" +
//        "    \"day\": \"Patchy heavy snow\",\n" +
//        "    \"night\": \"Patchy heavy snow\",\n" +
//        "    \"icon\": 335\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1225,\n" +
//        "    \"day\": \"Heavy snow\",\n" +
//        "    \"night\": \"Heavy snow\",\n" +
//        "    \"icon\": 338\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1237,\n" +
//        "    \"day\": \"Ice pellets\",\n" +
//        "    \"night\": \"Ice pellets\",\n" +
//        "    \"icon\": 350\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1240,\n" +
//        "    \"day\": \"Light rain shower\",\n" +
//        "    \"night\": \"Light rain shower\",\n" +
//        "    \"icon\": 353\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1243,\n" +
//        "    \"day\": \"Moderate or heavy rain shower\",\n" +
//        "    \"night\": \"Moderate or heavy rain shower\",\n" +
//        "    \"icon\": 356\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1246,\n" +
//        "    \"day\": \"Torrential rain shower\",\n" +
//        "    \"night\": \"Torrential rain shower\",\n" +
//        "    \"icon\": 359\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1249,\n" +
//        "    \"day\": \"Light sleet showers\",\n" +
//        "    \"night\": \"Light sleet showers\",\n" +
//        "    \"icon\": 362\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1252,\n" +
//        "    \"day\": \"Moderate or heavy sleet showers\",\n" +
//        "    \"night\": \"Moderate or heavy sleet showers\",\n" +
//        "    \"icon\": 365\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1255,\n" +
//        "    \"day\": \"Light snow showers\",\n" +
//        "    \"night\": \"Light snow showers\",\n" +
//        "    \"icon\": 368\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1258,\n" +
//        "    \"day\": \"Moderate or heavy snow showers\",\n" +
//        "    \"night\": \"Moderate or heavy snow showers\",\n" +
//        "    \"icon\": 371\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1261,\n" +
//        "    \"day\": \"Light showers of ice pellets\",\n" +
//        "    \"night\": \"Light showers of ice pellets\",\n" +
//        "    \"icon\": 374\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1264,\n" +
//        "    \"day\": \"Moderate or heavy showers of ice pellets\",\n" +
//        "    \"night\": \"Moderate or heavy showers of ice pellets\",\n" +
//        "    \"icon\": 377\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1273,\n" +
//        "    \"day\": \"Patchy light rain with thunder\",\n" +
//        "    \"night\": \"Patchy light rain with thunder\",\n" +
//        "    \"icon\": 386\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1276,\n" +
//        "    \"day\": \"Moderate or heavy rain with thunder\",\n" +
//        "    \"night\": \"Moderate or heavy rain with thunder\",\n" +
//        "    \"icon\": 389\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1279,\n" +
//        "    \"day\": \"Patchy light snow with thunder\",\n" +
//        "    \"night\": \"Patchy light snow with thunder\",\n" +
//        "    \"icon\": 392\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"code\": 1282,\n" +
//        "    \"day\": \"Moderate or heavy snow with thunder\",\n" +
//        "    \"night\": \"Moderate or heavy snow with thunder\",\n" +
//        "    \"icon\": 395\n" +
//        "  }\n" +
//        "]"
//
//val turnsType = object : TypeToken<codes>() {}.type
//val weathercode = Gson().fromJson(jsonstring, turnsType::class.java)

fun imagecode(it : Int?): Int{
    return when(it){
        1000 -> R.drawable.ic_sunny
        1003 -> R.drawable.partly_cloudy
        1006 -> R.drawable.cloudy
        1009 -> R.drawable.cloudy
        1030 -> R.drawable.fog
        1063 -> R.drawable.ic_rainshower
        1087,1273,1276,1279, -> R.drawable.ic_thunder
        1117 -> R.drawable.ic_heavysnow
        1135 -> R.drawable.fog
        1158,1153,1168,1171,1183,1180,1186,1189,1246,1249 -> R.drawable.ic_rainshower
        1192,1195 -> R.drawable.ic_rainy
        1198 -> R.drawable.ic_snowyrainy
        else -> R.drawable.ic_heavysnow
    }
}













