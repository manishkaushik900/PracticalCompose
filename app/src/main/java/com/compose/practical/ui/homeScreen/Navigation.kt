package com.compose.practical.ui.homeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Home.path
    ) {

        navigation(
            startDestination = Destinations.Feed.path,
            route = Destinations.Home.path
        ) {
            composable(route = Destinations.Feed.path) {

                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destinations = Destinations.Feed
                )
            }

            composable(route = Destinations.Contacts.path) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destinations = Destinations.Contacts
                )
            }

            composable(route = Destinations.Calendar.path) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destinations = Destinations.Calendar
                )
            }
        }


        composable(route = Destinations.Upgrade.path) {
            ContentArea(
                modifier = Modifier.fillMaxSize(),
                Destinations.Upgrade
            )
        }
        composable(route = Destinations.Settings.path) {
            ContentArea(
                modifier = Modifier.fillMaxSize(),
                Destinations.Settings
            )
        }

        navigation(
            startDestination = Destinations.Add.path,
            route = Destinations.Creation.path
        ) {
            composable(route = Destinations.Add.path) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destinations = Destinations.Add
                )
            }
        }

    }

}

//fun addStrings(a: String, b: String): String {
//    val maxLength = maxOf(a.length, b.length)
//    val sb = StringBuilder(maxLength)
//
//    var carry = 0
//    var i = a.length - 1
//    var j = b.length - 1
//
//    while (i >= 0 || j >= 0 || carry != 0) {
//        val digitA = if (i >= 0) a[i] - '0' else 0
//        val digitB = if (j >= 0) b[j] - '0' else 0
//
//        val sum = digitA + digitB + carry
//        carry = sum / 10
//        val digitSum = sum % 10
//
//        sb.append(digitSum)
//        i--
//        j--
//    }
//
//    return sb.reverse().toString()
//}
//
//fun main() {
//    val a = "1234"
//    val b = "993"
//    val result = addStrings(a, b)
//    println(result) // Output: 2233
//}