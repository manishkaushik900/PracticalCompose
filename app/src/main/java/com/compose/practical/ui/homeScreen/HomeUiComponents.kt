package com.compose.practical.ui.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.practical.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        Home()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    val viewModel: HomeViewModel = viewModel()
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val currentDestination by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry.value?.destination?.route?.let {
                Destinations.fromString(it)
            } ?: run {
                Destinations.Home
            }
        }
    }


//    val scaffoldState = rememberScaffoldState()
    var clickCount by remember { mutableStateOf(0) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier,
        topBar = {

            val snackbarMessage = stringResource(id = R.string.not_available_yet)

            CenterAlignedTopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                MaterialTheme.colorScheme.surfaceTint
            ),
                title = {
                    Text(text = "Home")
                },
                actions = {

                    if (currentDestination != Destinations.Feed) {
                        IconButton(onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "$snackbarMessage # ${++clickCount}"
                                )
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = stringResource(
                                    id = R.string.cd_more_information
                                )
                            )
                        }
                    }
                }
            )
        },/* floatingActionButton = {
        FloatingActionButton(onClick = { *//*TODO*//* }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.cd_create_item)
            )
        }
    },*/
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination, onNavigate = {
                    navController.navigate(it.path) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                    }
                }
            )
        }

    ) {

        Navigation(
            modifier = modifier.padding(it), navController = navController
        )
    }


}


@Composable
fun ContentArea(
    modifier: Modifier = Modifier, destinations: Destinations
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        destinations.icon?.let { icon ->
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = icon,
                contentDescription = destinations.path
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(text = destinations.path, fontSize = 18.sp)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BottomAPPPreview() {
    MaterialTheme {
        BottomNavigationBar(currentDestination = Destinations.Contacts,
            onNavigate = {})
    }

}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destinations,
    onNavigate: (destination: Destinations) -> Unit
) {


    BottomAppBar(
        modifier = modifier,
        actions = {
            listOf(
                Destinations.Feed,
                Destinations.Contacts,
                Destinations.Calendar
            ).forEach {

                NavigationBarItem(selected = currentDestination.path == it.path, icon = {
                    Icon(
                        imageVector = it.icon!!,
                        contentDescription = it.path,
                    )
                },
                    onClick = { onNavigate(it) },
                    label = { Text(text = it.path) })

            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}

