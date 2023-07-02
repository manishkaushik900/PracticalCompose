package com.compose.practical.ui.homeScreen.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val path: String,
    val icon: ImageVector? = null,
    val isRootDestination: Boolean = true

) {

    companion object {

        fun fromString(route: String): Destinations {

            return when (route) {
                Feed.path -> Feed
                Calendar.path -> Calendar
                Contacts.path -> Contacts
                Upgrade.path -> Upgrade
                Settings.path -> Settings
                Add.path -> Add
                Creation.path -> Creation
                else -> Home
            }
        }
    }

    val title = path.replaceFirstChar {
        it.uppercase()
    }


    object Home : Destinations("home")

    object Feed : Destinations(
        "feed",
        Icons.Default.List)

    object Contacts : Destinations(
        "contacts",
        Icons.Default.Person
    )

    object Calendar : Destinations(
        "calendar",
        Icons.Default.DateRange
    )

    object Settings : Destinations(
        "settings",
        Icons.Default.Settings,
        isRootDestination = false
    )
    object Upgrade : Destinations("upgrade",
        Icons.Default.Star,
        isRootDestination = false
    )

    object Creation : Destinations(
        path = "creation",
        isRootDestination = false
    )

    object Add : Destinations(
        path = "add",
        icon = Icons.Default.Add,
        isRootDestination = false
    )
}