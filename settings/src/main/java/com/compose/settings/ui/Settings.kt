package com.compose.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.settings.R
import com.compose.settings.ui.Tags.TAG_CHECK_ITEM
import com.compose.settings.ui.Tags.TAG_MARKETING_OPTION
import com.compose.settings.ui.Tags.TAG_SELECT_THEME
import com.compose.settings.ui.Tags.TAG_THEME_DROPDOWN_OPTION
import com.compose.settings.ui.Tags.TAG_THEME_OPTION
import com.compose.settings.ui.Tags.TAG_TOGGLE_ITEM

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
                onCheckedChanged = viewModel::toggleHintSettings
            )
            Divider()

            ManageSubscriptionItem(modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.setting_manage_subscription),
                onSettingClicked = {
                })
            Divider()

            SectionSpacer(modifier = Modifier.fillMaxWidth())
            MarketingSettingItem(
                modifier = Modifier.fillMaxWidth(),
                selectedOption = state.marketingOption,
                onOptionSelected = viewModel::setMarketingSettings
            )
            Divider()

            ThemeSettingItem(
                modifier = Modifier.fillMaxWidth(),
                selectedTheme = state.themeOption,
                onOptionSelected = viewModel::setTheme
            )
            Divider()

            SectionSpacer(modifier = Modifier.fillMaxWidth())

            AppVersionSettingItem(
                modifier = Modifier.fillMaxWidth(),
                stringResource(id = R.string.setting_app_version)
            )
            Divider()


        }

    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier, content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.heightIn(min = 56.dp)
    ) {
        content()
    }

}

@Composable
fun SectionSpacer(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier
            .height(48.dp)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.10f))
    )
}

// ThemeSettingItem.kt

@Composable
fun AppVersionSettingItem(
    modifier: Modifier = Modifier,
    appVersion: String
) {
    SettingItem(modifier = modifier) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .semantics(mergeDescendants = true) { },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.setting_app_version_title),
                modifier = Modifier.weight(1f)
            )
            Text(text = appVersion)
        }

    }
}

@Composable
fun ThemeSettingItem(
    modifier: Modifier = Modifier,
    selectedTheme: Theme,
    onOptionSelected: (option: Theme) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    SettingItem(modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable(
                    onClick = { expanded = !expanded },
                    onClickLabel = stringResource(id = R.string.cd_select_theme)
                )
                .padding(16.dp)
                .testTag(TAG_SELECT_THEME)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.setting_option_theme)
            )
            Text(
                modifier = Modifier.testTag(Tags.TAG_THEME_OPTION),
                text = stringResource(id = selectedTheme.label)

            )
        }
        DropdownMenu(
            modifier = Modifier.testTag(Tags.TAG_THEME_DROPDOWN),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(16.dp, 0.dp)
        ) {
            Theme.values().forEach { theme ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = theme.label)) },
                    onClick = {
                        onOptionSelected(theme)
                        expanded = false
                    },
                    modifier = Modifier.testTag(TAG_THEME_DROPDOWN_OPTION + theme.label)
                )

            }
        }
    }

}

@Composable
fun MarketingSettingItem(
    modifier: Modifier = Modifier,
    selectedOption: MarketingOption,
    onOptionSelected: (option: MarketingOption) -> Unit
) {

    val options = stringArrayResource(id = R.array.setting_options_marketing_choice)

    SettingItem(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.setting_option_marketing))
            Spacer(modifier = Modifier.height(8.dp))
            options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier
                        .testTag(TAG_MARKETING_OPTION + index)
                        .selectable(
                            selected = selectedOption.id == index,
                            onClick = {
                                val marketingOption = if (index == MarketingOption.ALLOWED.id) {
                                    MarketingOption.ALLOWED
                                } else {
                                    MarketingOption.NOT_ALLOWED
                                }
                                onOptionSelected(marketingOption)
                            },
                            role = Role.RadioButton
                        )
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    RadioButton(
                        selected = selectedOption.id == index, onClick = null
                    )
                    Text(
                        modifier = Modifier.padding(
                            start = 18.dp
                        ), text = option
                    )

                }
            }
        }
    }
}

@Composable
fun ManageSubscriptionItem(
    modifier: Modifier = Modifier, title: String, onSettingClicked: () -> Unit
) {
    SettingItem(modifier = modifier) {

        Row(modifier = Modifier
            .clickable(
                onClickLabel = stringResource(
                    id = R.string.cd_open_subscription
                )
            ) {
                onSettingClicked()
            }
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight, contentDescription = stringResource(
                    id = R.string.cd_open_subscription
                )
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
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

        )/*{

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
        Row(modifier = Modifier
            .testTag(TAG_TOGGLE_ITEM)
            .toggleable(
                value = checked, onValueChange = {
                    onCheckedChanged(it)
                }, role = Role.Switch
            )
            .semantics { stateDescription = notificationsEnabledState }
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, modifier = Modifier.weight(1f))
            Switch(checked = checked, onCheckedChange = null)
        }
    }
}

@Composable
fun HintSettingItem(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onCheckedChanged: (checked: Boolean) -> Unit
) {
    val hintEnabledState = if (checked) {
        stringResource(id = R.string.cd_notifications_enabled)
    } else stringResource(id = R.string.cd_notifications_disabled)

    SettingItem(modifier = modifier) {

        Row(modifier = Modifier
            .testTag(TAG_CHECK_ITEM)
            .toggleable(
                value = checked, onValueChange = {
                    onCheckedChanged(it)
                }, role = Role.Checkbox
            )
            .semantics { stateDescription = hintEnabledState }
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title, modifier = Modifier.weight(1f)
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






















