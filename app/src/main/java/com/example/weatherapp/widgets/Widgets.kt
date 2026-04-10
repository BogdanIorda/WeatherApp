package com.example.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.utils.formatDateWeek
import com.example.weatherapp.utils.formateDateTime
import com.example.weatherapp.utils.formateDecimals

@Composable
fun HumidityPressureWindRow(weather: WeatherItem, isImperial: Boolean) {

    val humidity = weather.humidity.toString()
    val psi = weather.pressure.toString()
    val windSpeed = formateDecimals(weather.speed)

    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier
                    .size(20.dp)
            )
            Text(text = "$humidity%")
        }
        Row() {
            Image(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = "$psi psi"
            )
        }
        Row() {
            Image(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind icon",
                modifier = Modifier
                    .size(20.dp)
            )
            Text(text = windSpeed + if (isImperial) "mph" else "m/s")
        }
    }
}

@Composable
fun SunSetSunRise(weather: WeatherItem) {

    val sunriseTime = formateDateTime(weather.sunrise)
    val sunsetTime = formateDateTime(weather.sunset)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically // just in case
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier
                    .size(25.dp)
            )
            Text(
                text = sunriseTime,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row() {
            Text(
                text = sunsetTime,
                style = MaterialTheme.typography.bodyMedium
            )
            Image(
                painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier
                    .size(25.dp)
            )
        }
    }
}


@Composable
fun WeatherDetailRow(weatherObj: WeatherItem) {

    val minTemp = formateDecimals(weatherObj.temp.min)
    val maxTemp = formateDecimals(weatherObj.temp.max)
    val currentDay = formatDateWeek(weatherObj.dt)
    val weatherDescription = weatherObj.weather[0].description
    val eachDayImageUrl = "https://openweathermap.org/img/wn/${weatherObj.weather[0].icon}.png"

    val halfAndHalfColoredText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xCC311EFD))) {
            append("${maxTemp}°")
        }
        withStyle(style = SpanStyle(color = Color(0xCB9E9E9E))) {
            append("$minTemp°")
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        shape = CircleShape.copy(
            topEnd = CornerSize(8.dp)
        ),
        color = MaterialTheme.colorScheme.background,

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = currentDay)
            WeatherStateImage(
                eachDayImageUrl,
                modifier = Modifier
                    .size(50.dp)
            )
            Surface(
                modifier = Modifier
                    .padding(4.dp),
                shape = CircleShape,
                color = Color(0xFFFFCE2E),
            ) {
                Text(
                    text = weatherDescription,
                    modifier = Modifier
                        .padding(4.dp),
                    fontWeight = FontWeight.ExtraLight
                )
            }
            Text(text = halfAndHalfColoredText)
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Icon image",
        modifier = modifier
    )
}
