package com.compose.practical.ui.homeScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.compose.practical.R
import com.compose.practical.ui.homeScreen.model.Destinations
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootTopBar(
    snackbarHostState:SnackbarHostState,
    currentDestination: Destinations,
    drawerState: DrawerState
){
    val scope = rememberCoroutineScope()
    var clickCount by remember { mutableStateOf(0) }
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
}