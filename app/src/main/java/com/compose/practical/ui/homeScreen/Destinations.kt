package com.compose.practical.ui.homeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
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
                Creation.path -> Creation
                else -> Home
            }
        }

    }


    object Home : Destinations("home")

    object Feed : Destinations("feed", Icons.Default.List)

    object Contacts : Destinations(
        "contacts", Icons.Default.Person
    )

    object Calendar : Destinations(
        "calendar", Icons.Default.DateRange
    )

    object Creation : Destinations(
        path = "creation",
        icon = Icons.Default.Add,
        isRootDestination = false
    )
}