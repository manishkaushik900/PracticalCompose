package com.compose.settings.settingFeatures

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.compose.settings.ui.HintSettingItem
import com.compose.settings.ui.Tags.TAG_CHECK_ITEM
import org.junit.Rule
import org.junit.Test

class HintsSettingItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun Title_Displayed() {
        val title = "Show Hints"
        composeTestRule.setContent {
            HintSettingItem(
                title = title,
                checked = true,
                onCheckedChanged = {}
            )
        }
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }


    @Test
    fun Setting_Checked() {
        composeTestRule.setContent {
            HintSettingItem(
                title = "Show Hints",
                checked = true,
                onCheckedChanged = { }
            )
        }
        composeTestRule
            .onNodeWithTag(TAG_CHECK_ITEM)
            .assertIsOn()
    }

}