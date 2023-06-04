package com.compose.settings.ui

import androidx.annotation.StringRes
import com.compose.settings.R

/*setting states*/
data class SettingsState(
    val notificationEnabled: Boolean = false,
    val hintsEnabled: Boolean = false,
    val marketingOption: MarketingOption = MarketingOption.ALLOWED,
    val themeOption: Theme = Theme.SYSTEM
)

enum class MarketingOption(val id: Int) {
    ALLOWED(0), NOT_ALLOWED(1)
}

enum class Theme(@StringRes val label: Int) {
    LIGHT(R.string.theme_light),
    DARK(R.string.theme_dark),
    SYSTEM(R.string.theme_system)
}
