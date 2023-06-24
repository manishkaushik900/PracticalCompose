package com.compose.practical.ui.homeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Feed.path
    ) {

        composable(route = Destinations.Feed.path) {

            ContentArea(
                destinations = Destinations.Feed
            )
        }

        composable(route = Destinations.Contacts.path) {
            ContentArea(
                destinations = Destinations.Contacts
            )
        }

        composable(route = Destinations.Calendar.path) {
            ContentArea(
                destinations = Destinations.Calendar
            )
        }
    }

}