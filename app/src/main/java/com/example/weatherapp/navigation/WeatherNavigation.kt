package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screens.about.AboutScreen
import com.example.weatherapp.screens.favorites.FavoriteScreen
import com.example.weatherapp.screens.main.MainScreen
import com.example.weatherapp.screens.main.MainViewModel
import com.example.weatherapp.screens.search.SearchScreen
import com.example.weatherapp.screens.settings.SettingScreen
import com.example.weatherapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {

        composable(route = WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            "$route/{city}", arguments = listOf(
                navArgument("city") {
                    type = NavType.StringType
                }
            )) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }
        }

        composable(route = WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(route = WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(route = WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }

        composable(route = WeatherScreens.SettingsScreen.name) {
            SettingScreen(navController = navController)
        }
    }
}


