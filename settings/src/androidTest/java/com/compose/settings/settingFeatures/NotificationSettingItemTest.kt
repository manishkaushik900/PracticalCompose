package com.compose.settings.settingFeatures

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.compose.settings.ui.NotificationSetting
import com.compose.settings.ui.Tags
import org.junit.Rule
import org.junit.Test

class NotificationSettingItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun Title_Displayed(){
        val title = "Enable Notification"
        composeTestRule.setContent {
            NotificationSetting(title = title, checked =true , onCheckedChanged ={} )
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()

    }

    @Test
    fun Setting_Checked() {
        composeTestRule.setContent {
            NotificationSetting(
                title = "Enable Notifications",
                checked = true,
                onCheckedChanged = { }
            )
        }

        composeTestRule.onNodeWithTag(
            Tags.TAG_TOGGLE_ITEM
        ).assertIsOn()
    }

}