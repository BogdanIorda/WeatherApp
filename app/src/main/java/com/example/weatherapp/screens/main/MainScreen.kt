package com.example.weatherapp.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.components.DividerLine
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.model.WeatherObject
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.settings.SettingsViewModel
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.formateDecimals
import com.example.weatherapp.widgets.HumidityPressureWindRow
import com.example.weatherapp.widgets.SunSetSunRise
import com.example.weatherapp.widgets.WeatherAppBar
import com.example.weatherapp.widgets.WeatherDetailRow
import com.example.weatherapp.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {

    val curCity = if (city.isNullOrBlank()) "Austin" else city

    val lastUpdate = mainViewModel.lasUpdateTime.value

    val unitFromDb = settingsViewModel.unitsList.collectAsState().value
    val unit = if (unitFromDb.isNotEmpty()) {
        unitFromDb[0].unit.split(" ")[0].lowercase()
    } else {
        "imperial"
    }
    val isImperial = unit == "imperial"

    LaunchedEffect(key1 = curCity, key2 = unit) {
        mainViewModel.getWeather(curCity, units = unit)
    }
    val weatherData = mainViewModel.data.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(
            weather = weatherData.data!!,
            navController = navController,
            isImperial = isImperial,
            updateTime = lastUpdate
        )
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    weather: WeatherObject,
    navController: NavController,
    isImperial: Boolean,
    updateTime: String
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ", ${weather.city.country}",
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                elevation = 10.dp,
            ) {
                Log.d("Button", "MainScaffold: Button Clicked")
            }
        }) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            MainContent(
                data = weather,
                isImperial = isImperial,
                updateTime = updateTime
            )
        }
    }
}

@Composable
fun MainContent(data: WeatherObject, isImperial: Boolean, updateTime: String) {

    val weatherItem = data.list[0]
    val currentDayImageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    val date = formatDate(weatherItem.dt)
    val dayTemp = formateDecimals(weatherItem.temp.day)
    val weather = weatherItem.weather[0].main

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC61C)
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                WeatherStateImage(
                    imageUrl = currentDayImageUrl,
                    modifier = Modifier
                        .size(60.dp)
                )

                Text(
                    text = "$dayTemp°",
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = weather,
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        HumidityPressureWindRow(weather = weatherItem, isImperial = isImperial)

        DividerLine() //HorizontalDivider() -> default way of doing it(easier but less customizable)

        SunSetSunRise(weather = weatherItem)

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "This Week")

            Text(
                text = "Last updated: $updateTime",
                fontWeight = FontWeight.Thin
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color(0xFFE3E3E3),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(2.dp)
            ) {
                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(weatherObj = item)
                }
            }
        }
    }
}

























