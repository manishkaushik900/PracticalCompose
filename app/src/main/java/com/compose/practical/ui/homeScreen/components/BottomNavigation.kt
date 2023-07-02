package com.compose.practical.ui.homeScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.compose.practical.R
import com.compose.practical.ui.homeScreen.Tags.TAG_BOTTOM_NAVIGATION
import com.compose.practical.ui.homeScreen.model.Destinations

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destinations,
    onNavigate: (destination: Destinations) -> Unit,
    onFloatingBtnClick: () -> Unit
) {
    BottomAppBar(
        modifier = modifier.testTag(TAG_BOTTOM_NAVIGATION),
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
                    label = { Text(text = it.title) })

            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFloatingBtnClick() },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.cd_create_item))
            }
        }
    )
}