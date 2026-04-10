package com.example.weatherapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DividerLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        val canvasWidth = size.width
        drawLine(
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = canvasWidth, y = 0f),
            color = Color.LightGray,
            strokeWidth = 4F,
        )
    }

}