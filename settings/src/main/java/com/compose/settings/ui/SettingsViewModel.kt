package com.compose.settings.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel : ViewModel() {

    val uiState = MutableStateFlow(SettingsState())

    fun toggleNotificationSettings(boolean: Boolean) {
        uiState.value = uiState.value.copy(
            notificationEnabled =!uiState.value.notificationEnabled
        )

    }

    fun toggleHintSettings(boolean: Boolean) {
        uiState.value = uiState.value.copy(hintsEnabled = !
        uiState.value.hintsEnabled)
    }

    fun setMarketingSettings(option: MarketingOption) {
        uiState.value = uiState.value.copy(
            marketingOption = option)
    }

    fun setTheme(theme: Theme) {
        uiState.value = uiState.value.copy(themeOption = theme)
    }

}