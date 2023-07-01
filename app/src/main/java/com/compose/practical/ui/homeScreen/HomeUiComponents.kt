@file:OptIn(ExperimentalMaterial3Api::class)

package com.compose.practical.ui.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.practical.R
import kotlinx.coroutines.launch
import java.util.Locale


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


@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    onNavigationSelected: (destination: Destinations) -> Unit,
    onLogout: () -> Unit,
    selectedItem: Destinations
) {
    val items =
        listOf(
            Destinations.Upgrade,
            Destinations.Settings
        )
    ModalDrawerSheet(modifier = modifier) {
        Spacer(Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.label_name),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.label_email_drawer),
            fontSize = 16.sp
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon!!, contentDescription = null) },
                label = { Text(item.path) },
                selected = item.path == selectedItem.path,
                onClick = {
                    onNavigationSelected(item)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Logout, contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.log_out)) },
            selected = false,
            onClick = {
                onLogout()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    currentDestination: Destinations
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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
                    Text(
                        text = currentDestination.path.replaceFirstChar { char ->
                        char.titlecase(Locale.getDefault())
                    })
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(
                                id = R.string.cd_open_menu
                            )
                        )

                    }
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

@Composable
fun ContentArea(
    modifier: Modifier = Modifier,
    destinations: Destinations
) {
    Column(
        modifier = modifier,
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


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destinations,
    onNavigate: (destination: Destinations) -> Unit,
    onFloatingBtnClick: () -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            listOf(
                Destinations.Feed,
                Destinations.Contacts,
                Destinations.Calendar
            ).forEach {

                NavigationBarItem(
                    selected = currentDestination.path == it.path,
                    icon = {
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
                onClick = { onFloatingBtnClick() },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, stringResource(R.string.cd_create_item))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BottomAPPPreview() {
    MaterialTheme {
        BottomNavigationBar(currentDestination = Destinations.Contacts,
            onNavigate = {}, onFloatingBtnClick = {})
    }

}