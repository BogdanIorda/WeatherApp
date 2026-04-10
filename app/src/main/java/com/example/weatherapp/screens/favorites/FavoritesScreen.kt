package com.example.weatherapp.screens.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorite Cities",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            isMainScreen = false,
            elevation = 10.dp,
            navController = navController
        )
        { navController.popBackStack() }
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoritesViewModel.favoritesList.collectAsState().value

                LazyColumn() {
                    items(items = list) {
                        CityRow(it, navController = navController, favoritesViewModel)
                    }
                }
            }
        }

    }
}


@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(8.dp)),
        color = Color(0xA774BDB5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = favorite.city,
                fontWeight = FontWeight.W200,
            )

            Surface(
                modifier = Modifier
                    .padding(4.dp),
                shape = CircleShape,
                color = Color(0xF0D7D7D7)
            ) {
                Text(
                    text = favorite.country,
                    modifier = Modifier
                        .padding(4.dp),
                    fontWeight = FontWeight.W100,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                modifier = Modifier
                    .clickable {
                        favoritesViewModel.removeFavorite(favorite)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = "Trash Container",
                tint = Color.Red.copy(alpha = 0.4f)
            )
        }
    }
}
















