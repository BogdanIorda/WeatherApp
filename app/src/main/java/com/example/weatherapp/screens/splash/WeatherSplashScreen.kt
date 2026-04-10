package com.example.weatherapp.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    val defaultCity = "Austin"
    val scaleState = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(true, block = {
        scaleState.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            ))
        delay(1500)

        navController.navigate(WeatherScreens.MainScreen.name + "/$defaultCity")
    })


    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scaleState.value),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        border = BorderStroke(2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "Sunny icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
            )
            Text(
                text = "Find the Sun?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Thin,
                color = Color.Black
            )

        }
    }
}