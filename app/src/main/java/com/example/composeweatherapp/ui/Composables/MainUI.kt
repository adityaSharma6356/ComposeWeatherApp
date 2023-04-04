package com.example.composeweatherapp.ui.Composables

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composeweatherapp.BottomWindow
import com.example.composeweatherapp.MainViewModel
import com.example.composeweatherapp.R
import com.example.composeweatherapp.ui.theme.temfamily

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    mainViewModel: MainViewModel,
    context: Context,
) {
    BackHandler(enabled = mainViewModel.backHandler) {
        mainViewModel.changeBarHeight()
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            GlideImage(
                model = mainViewModel.backgroundImageState,
                contentDescription = "background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        }
        val (hilo ,dayComment ,currentTemp, precipitationChance, currentTimeDate, currentDay, airQuality, progressBar, weatherInfo, weatherIcon, expandableBar, locationName) = createRefs()
        val interactionSource = MutableInteractionSource()
        val tempsize by animateIntAsState(
            targetValue = if(mainViewModel.currentUIState){
                70
            } else {
                100
            },
            animationSpec = tween(700)
        )
        val templeft by animateIntAsState(
            targetValue = if(mainViewModel.currentUIState){
                20
            } else {
                50
            },
            animationSpec = tween(700)
        )
        val height by animateIntAsState(
            targetValue = if(mainViewModel.currentUIState){
                220
            } else {
                1000
            },
            animationSpec = tween(700)
        )
        val temppad by animateIntAsState(
            targetValue = if(mainViewModel.currentUIState){
                80
            } else {
                200
            },
            animationSpec = tween(700)
        )
        val dateTimepad by animateIntAsState(
            targetValue = if(mainViewModel.currentUIState){
                0
            } else {
                255
            },
            animationSpec = tween(700)
        )
        val blurheight by animateDpAsState(
            targetValue = if(mainViewModel.currentUIState){
                0
            } else {
                780
            }.dp,
            animationSpec = tween(700)
        )
        Text(text = "${mainViewModel.currentTemp}°",
            modifier = Modifier
                .constrainAs(currentTemp) {
                    top.linkTo(parent.top, margin = temppad.dp)
                    start.linkTo(parent.start, margin = templeft.dp)
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
            color = Color.White
        )
        Text(
            text = mainViewModel.dayComment(),
            color = Color.White,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            modifier = Modifier.constrainAs(dayComment) {
                start.linkTo(currentTemp.end, margin = 5.dp)
                bottom.linkTo(currentTemp.bottom, margin = 0.dp)
            }
        )
        Text(
            text = mainViewModel.feelslike(),
            color = Color.White,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            modifier = Modifier.constrainAs(hilo) {
                start.linkTo(currentTemp.end, margin = 5.dp)
                bottom.linkTo(dayComment.top, margin = 5.dp)
            }
        )
        Text(
            text = "${mainViewModel.currentDay}/${mainViewModel.currentMonth}/${mainViewModel.currentYear}",
            modifier = Modifier
                .constrainAs(currentTimeDate) {
                    bottom.linkTo(currentTemp.top)
                    start.linkTo(parent.start, margin = 50.dp)
                },
            fontSize = 20.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            color = Color(255, 255, 255, dateTimepad)
        )
        Text(
            text = mainViewModel.currentDayName,
            modifier = Modifier
                .constrainAs(currentDay) {
                    top.linkTo(currentTimeDate.bottom)
                    start.linkTo(parent.start, margin = 50.dp)
                },
            fontSize = 18.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            color = Color(255, 255, 255, dateTimepad)
        )
        Text(
            text = mainViewModel.currentLocationName,
            modifier = Modifier
                .constrainAs(locationName) {
                    bottom.linkTo(currentTimeDate.top)
                    start.linkTo(parent.start, margin = 50.dp)
                },
            fontSize = 18.sp,
            fontFamily = temfamily,
            fontWeight = FontWeight.Normal,
            color = Color(255, 255, 255, dateTimepad)
        )

        Text(
            text = "AQI " + mainViewModel.currentAQI
                .toString(),
            modifier = Modifier
                .constrainAs(airQuality) {
                    top.linkTo(parent.top, margin = 55.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                },
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Thin,
            color = Color.White
        )
        Image(
            painter = painterResource(id = mainViewModel.imagecode(mainViewModel.weathericoncode, mainViewModel.currentDateTime)),
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
        )

        Card(modifier = Modifier
            .constrainAs(precipitationChance) {
                top.linkTo(weatherIcon.bottom, margin = 10.dp)
                end.linkTo(parent.end, margin = 15.dp)
            }
            .height(35.dp)
            .width(100.dp),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wind_icon),
                    contentDescription = "drop icon",
                    modifier = Modifier
                        .size(25.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = mainViewModel.state!!.weatherInfo.current.wind_kph.toInt()
                        .toString() + "k/h",
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Thin,
                    color = Color.White
                )
            }
        }
        CircularProgressIndicator(
            modifier = Modifier
                .size(20.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    mainViewModel.loadWeatherInfo(context)
                    mainViewModel.state = mainViewModel.state
                }
                .constrainAs(progressBar) {
                    top.linkTo(precipitationChance.bottom, margin = 10.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                    visibility =
                        if (mainViewModel.progressState) Visibility.Visible else Visibility.Invisible
                },
            color = Color.White,
            strokeWidth = 1.dp
        )
        mainViewModel.state!!.weatherInfo.current.condition.let {
            Text(
                text = it.text,
                modifier = Modifier
                    .constrainAs(weatherInfo) {
                        top.linkTo(parent.top, margin = 55.dp)
                        end.linkTo(weatherIcon.start, margin = 10.dp)
                    },
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Thin,
                color = Color.White
            )
        }
        Card(
            modifier = Modifier
                .constrainAs(expandableBar) {
                    top.linkTo(parent.top, margin = height.dp)
                }
                .fillMaxWidth()
                .height(800.dp),
            elevation = CardDefaults.cardElevation(15.dp),
            shape = RoundedCornerShape(35.dp),
        ) {
            BottomWindow(mainViewModel = mainViewModel, blurheight = blurheight)
        }
    }
}