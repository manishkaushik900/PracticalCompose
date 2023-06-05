package com.compose.settings.settingFeatures

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.compose.settings.ui.AppVersionSettingItem
import org.junit.Rule
import org.junit.Test

class AppVersionSettingItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun App_Version_Displayed() {
        val version = "1.0.4"
        composeTestRule.setContent {
            AppVersionSettingItem(appVersion = version)
        }
        composeTestRule
            .onNodeWithText(version)
            .assertIsDisplayed()
    }
}