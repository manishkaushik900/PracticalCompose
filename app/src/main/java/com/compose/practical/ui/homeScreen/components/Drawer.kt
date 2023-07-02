package com.compose.practical.ui.homeScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.practical.R
import com.compose.practical.ui.homeScreen.model.Destinations

@OptIn(ExperimentalMaterial3Api::class)
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