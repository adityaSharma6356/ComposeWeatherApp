package com.example.composeweatherapp.ui.Composables

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composeweatherapp.MainViewModel
import com.example.composeweatherapp.R
import com.example.composeweatherapp.ui.theme.WeatherType
import com.example.composeweatherapp.ui.theme.temfamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    mainViewModel: MainViewModel,
    context: Context,
){

    val systemUiController = rememberSystemUiController()

    SideEffect {
        val window = (context as Activity).window
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        systemUiController.systemBarsDarkContentEnabled = mainViewModel.currentDateTime in 7..19
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            GlideImage(model = mainViewModel.backgroundImageState,
                contentDescription = "background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight)
        }
        val (currentTemp ,precipitationChance , currentTimeDate, currentDay,airQuality , progressBar, weatherInfo, weatherIcon, expandableBar, bottomBarTitle) = createRefs()
        val interactionSource = MutableInteractionSource()
        val temppad by animateDpAsState(
            targetValue = mainViewModel.tempAnimState.dp,
            animationSpec = tween(700)
        )
        val dateTimepad by animateDpAsState(
            targetValue = mainViewModel.dayDateleftpad.dp,
            animationSpec = tween(700)
        )
        val tempsize by animateIntAsState(
            targetValue = mainViewModel.tempsizestate,
            animationSpec = tween(700)
        )
        Text(text = "${mainViewModel.state.value.weatherInfo?.hourly?.temperature_2m?.get(mainViewModel.currentDateTime).toString()}°" ,
            modifier = Modifier
                .constrainAs(currentTemp) {
                    top.linkTo(parent.top, margin = temppad)
                    start.linkTo(parent.start, margin = 50.dp)
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    mainViewModel.changeBarHeight()
                },
            fontSize = tempsize.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Thin,
            color = Color.White)

        Text(text = "${mainViewModel.currentDay}/${mainViewModel.currentMonth}/${mainViewModel.currentYear}" ,
            modifier = Modifier
                .constrainAs(currentTimeDate){
                    bottom.linkTo(currentTemp.top,)
                    start.linkTo(parent.start, margin = dateTimepad)
                },
            fontSize = 20.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            color = Color.White)
        Text(text = mainViewModel.currentDayName,
            modifier = Modifier
                .constrainAs(currentDay){
                    top.linkTo(currentTimeDate.bottom,)
                    start.linkTo(parent.start, margin = dateTimepad)
                },
            fontSize = 18.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            color = Color.White)
        Text(text = "AQI 220",
            modifier = Modifier
                .constrainAs(airQuality){
                    top.linkTo(parent.top, margin = 55.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                },
            fontSize = 18.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            color = Color.White)
        mainViewModel.state.value.weatherInfo?.hourly?.weathercode?.get(mainViewModel.currentDateTime)?.let { WeatherType.fromWMO(it).iconRes }
            ?.let { painterResource(id = if(mainViewModel.currentDateTime in 7..19) it.day else it.night) }
            ?.let { Image(
                painter = it,
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .constrainAs(weatherIcon) {
                        end.linkTo(parent.end, margin = 15.dp)
                        top.linkTo(parent.top, margin = 55.dp)
                    }

                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        mainViewModel.changeBarHeight()
                    }
                    .size(30.dp),
            ) }
        Card(modifier = Modifier
            .constrainAs(precipitationChance) {
                top.linkTo(weatherIcon.bottom, margin = 10.dp)
                end.linkTo(parent.end, margin = 15.dp)
            }
            .height(35.dp)
            .width(80.dp),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp , Color.White),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_drop),
                    contentDescription = "drop icon",
                    modifier = Modifier
                        .size(25.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
                Text(text = "69%",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Thin,
                    color = Color.White)
            }
        }
        CircularProgressIndicator(
            modifier = Modifier
                .size(20.dp)
                .constrainAs(progressBar) {
                    top.linkTo(precipitationChance.bottom, margin = 10.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                    visibility =
                        if (mainViewModel.progressState) Visibility.Visible else Visibility.Invisible
                },
            color = Color.White,
            strokeWidth = 1.dp
        )
        Text(text = mainViewModel.state.value.weatherInfo?.hourly?.weathercode?.get(mainViewModel.currentDateTime)
            ?.let { WeatherType.fromWMO(it).weatherDesc }.toString(),
            modifier = Modifier
                .constrainAs(weatherInfo){
                    top.linkTo(parent.top, margin = 55.dp)
                    end.linkTo(weatherIcon.start, margin = 10.dp)
                },
            fontSize = 18.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            color = Color.White)

        val height by animateDpAsState(targetValue = mainViewModel.barHeightState.dp,
            animationSpec = tween(700)
        )

        val blurheight by animateDpAsState(targetValue = mainViewModel.blurImgState.dp,
            animationSpec = tween(700)
        )
        Card(modifier = Modifier
            .constrainAs(expandableBar) {
                top.linkTo(parent.top, margin = height)
            }
            .fillMaxWidth()
            .height(800.dp),
            shape = RoundedCornerShape(35.dp),
            border = BorderStroke(1.dp , Color.White),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (bgImage, todayTitle, todayInfo) = createRefs()
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
                    alignment = Alignment.BottomCenter)
            }

        }
//        Text(text = "Today's Forecast",
//            fontSize = 18.sp,
//            fontFamily = temfamily,
//            fontWeight = FontWeight.Normal,
//            color = Color.White)

    }
}