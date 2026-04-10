package com.example.weatherapp.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.favorites.FavoritesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {

    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }


    CenterAlignedTopAppBar(
        modifier = Modifier
            .shadow(elevation),
        windowInsets = WindowInsets(),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.background,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More icon")
                }
            } else Box() {}
        },
        navigationIcon = {
            if (icon != null) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = icon,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .clickable { onButtonClicked.invoke() },
                        contentDescription = null,
                    )
                }
            }
            if (isMainScreen) {
                val listOfFavoriteCities =
                    favoritesViewModel.favoritesList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }

                if (listOfFavoriteCities.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoritesViewModel.addFavorite(
                                    Favorite(
                                        city = dataList[0], // city name
                                        country = dataList[1] // country code
                                    )
                                )
                                Toast.makeText(
                                    context,
                                    "Added ${dataList[0].uppercase()} to Favorites",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                        tint = Color.Red.copy(alpha = 0.7f)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
    )
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember { mutableStateOf(true) }

    val items = listOf("About", "Favorites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 50.dp, right = 25.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDialog.value = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        when (text) {
                                            "About" -> WeatherScreens.AboutScreen.name
                                            "Favorites" -> WeatherScreens.FavoriteScreen.name
                                            else -> WeatherScreens.SettingsScreen.name
                                        }
                                    )
                                },
                            fontWeight = FontWeight.W300
                        )
                    },
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                    }
                )
            }
        }
    }
}
