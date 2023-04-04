package com.example.composeweatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composeweatherapp.ui.theme.temfamily

@Composable
fun BottomWindow(mainViewModel: MainViewModel, blurheight : Dp){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (bgImage, todayTitle, todayInfo, dayComment) = createRefs()
        Image(
            painter = painterResource(id = mainViewModel.backgroundImageBlurredState),
            contentDescription = "background",
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
                .constrainAs(bgImage) {
                    bottom.linkTo(parent.bottom, margin = blurheight)
                },
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter,
            colorFilter = ColorFilter.tint(Color(0,0,0,80), blendMode = BlendMode.Darken)
        )
        Text(
            text = "Today's Forecast ",
            color = Color.White,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            modifier = Modifier.constrainAs(todayTitle) {
                top.linkTo(parent.top, margin = 7.dp)
                start.linkTo(parent.start, margin = 20.dp)
            }
        )
        LazyRow(modifier = Modifier
            .constrainAs(todayInfo) {
                top.linkTo(parent.top, margin = 55.dp)
            }
            .height(200.dp)
            .fillMaxWidth()
            .background(Color.Transparent),
            contentPadding = PaddingValues(15.dp, 0.dp)) {
            items(mainViewModel.forcastsize) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(60.dp)
                        .fillMaxHeight()
                        .background(Color.Transparent)
                ) {
                    Text(
                        text = "$it:00",
                        color = Color.White,
                        fontSize = 12.sp,
                    )
                    Image(
                        painter = painterResource(id = mainViewModel.imagecode(mainViewModel.state!!.weatherInfo.forecast.forecastday[0].hour[it].condition.code, it)),
                        contentDescription = "icon",
                        modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 15.dp)
                    )
                    Text(
                        text = mainViewModel.state!!.weatherInfo.forecast.forecastday[0].hour[it].temp_c.toString() + "°",
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                    Row(modifier = Modifier.padding(0.dp , 15.dp , 0.dp , 15.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_drop),
                            contentDescription = "drop",
                            modifier = Modifier.size(15.dp),
                            tint = Color.White
                        )
                        Text(
                            text = mainViewModel.state!!.weatherInfo.forecast.forecastday[0].hour[it].will_it_rain.toString() + "%",
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}