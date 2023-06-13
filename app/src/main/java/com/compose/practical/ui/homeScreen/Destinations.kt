package com.compose.practical.ui.homeScreen

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val path: String,
    val icon: ImageVector? = null
){

    object Home: Destinations("home")
}