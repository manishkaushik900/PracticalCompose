@file:OptIn(ExperimentalMaterial3Api::class)

package com.compose.practical.ui.homeScreen

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.practical.ui.homeScreen.components.DrawerContent
import com.compose.practical.ui.homeScreen.components.HomeContent
import com.compose.practical.ui.homeScreen.model.Destinations
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry.value?.destination?.route?.let {
                Destinations.fromString(it)
            } ?: run {
                Destinations.Home
            }
        }
    }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                modifier = Modifier,
                onLogout = {
                    scope.launch { drawerState.close() }
                },
                onNavigationSelected = {
                    scope.launch { drawerState.close() }
                    navController.navigate(it.path)
                },
                selectedItem = currentDestination
            )
        },
        content = {
            HomeContent(
                drawerState = drawerState,
                navController = navController,
                currentDestination = currentDestination
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        Home()
    }

}