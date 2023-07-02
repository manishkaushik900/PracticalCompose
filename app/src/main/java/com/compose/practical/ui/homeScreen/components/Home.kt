package com.compose.practical.ui.homeScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compose.practical.ui.homeScreen.model.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    currentDestination: Destinations
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier,
        topBar = {

            RootTopBar(
                snackbarHostState = snackbarHostState,
                currentDestination =  currentDestination,
                drawerState = drawerState
            )
        },
        bottomBar = {
            /*val orientation = LocalConfiguration.current.orientation
            if (orientation != Configuration.ORIENTATION_LANDSCAPE
                && currentDestination.isRootDestination
            ) {*/
            BottomNavigationBar(
                currentDestination = currentDestination,
                onNavigate = {
                    navController.navigate(it.path) {
                        /* popUpTo(
                         navController.graph.findStartDestination().id
                     ) {
                         saveState = true
                     }*/
                        launchSingleTop = true
                        restoreState = true

                    }
                },
                onFloatingBtnClick = {
                    navController.navigate(Destinations.Creation.path)
                }
            )
//            }
        }

    ) {
        Navigation(
            modifier = modifier.padding(it), navController = navController
        )
    }
}