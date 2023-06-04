package com.compose.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.settings.R

@Composable
fun Settings() {
    val viewModel: SettingsViewModel = viewModel()

    MaterialTheme {
        val state = viewModel.uiState.collectAsState().value

        Column {
            TopAppBar()
            NotificationSetting(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.setting_enable_notifications),
                checked = state.notificationEnabled,
                onCheckedChanged = viewModel::toggleNotificationSettings
            )
            Divider()

            HintSettingItem(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.setting_enable_hint),
                checked = state.hintsEnabled,
                onCheckedChanged = viewModel::toggleHintSettings            )

            Divider()
        }

    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .heightIn(min = 56.dp)
    ) {
        content()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.verticalScroll(rememberScrollState()))
    {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.title_settings),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.cd_go_back)
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Localized description"
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.surface)

        )
        /*{

           Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.cd_go_back))

        }*/
    }
}


@Composable
fun NotificationSetting(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onCheckedChanged: (checked: Boolean) -> Unit
) {
    val notificationsEnabledState = if (checked) {
        stringResource(id = R.string.cd_notifications_enabled)
    } else stringResource(id = R.string.cd_notifications_disabled)

    SettingItem(modifier = modifier) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = checked,
                    onValueChange = onCheckedChanged,
                    role = Role.Switch
                )
                .semantics { stateDescription = notificationsEnabledState }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, modifier = Modifier.weight(1f))
            Switch(checked = checked, onCheckedChange = null)
        }
    }
}

@Composable
fun HintSettingItem(
    modifier: Modifier,
    title: String,
    checked: Boolean,
    onCheckedChanged: (checked: Boolean) -> Unit
) {
    val hintEnabledState = if (checked) {
        stringResource(id = R.string.cd_notifications_enabled)
    } else stringResource(id = R.string.cd_notifications_disabled)

    SettingItem(modifier = modifier) {

        Row(
            modifier = Modifier
                .toggleable(
                    value = checked,
                    onValueChange = onCheckedChanged,
                    role = Role.Checkbox
                ) .semantics { stateDescription = hintEnabledState }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f)
            )
            Checkbox(checked = checked, onCheckedChange = null)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        Settings()
    }
}






















