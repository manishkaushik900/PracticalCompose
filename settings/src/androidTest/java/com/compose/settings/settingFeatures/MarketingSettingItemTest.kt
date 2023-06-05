package com.compose.settings.settingFeatures

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.compose.settings.R
import com.compose.settings.ui.MarketingOption
import com.compose.settings.ui.MarketingSettingItem
import com.compose.settings.ui.Tags
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MarketingSettingItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun Title_Displayed(){
        val marketingOption: MarketingOption = MarketingOption.ALLOWED
        composeTestRule.setContent {
            MarketingSettingItem(selectedOption =marketingOption , onOptionSelected = {})
        }

        composeTestRule.onNodeWithText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.setting_option_marketing)
        ).assertIsDisplayed()
    }

    @Test
    fun Marketing_Option_Selected(){
        val marketingOption: MarketingOption = MarketingOption.NOT_ALLOWED
        composeTestRule.setContent {
            MarketingSettingItem(selectedOption =marketingOption , onOptionSelected = {})
        }

        composeTestRule.onNodeWithTag(Tags.TAG_MARKETING_OPTION + 1).assertIsSelected()
    }

    @Test
    fun On_Option_Change_Triggered(){
        val marketingOption: MarketingOption = MarketingOption.NOT_ALLOWED
        val onOptionSelected: (option: MarketingOption) -> Unit = mock()

        composeTestRule.setContent {
            MarketingSettingItem(selectedOption =marketingOption , onOptionSelected = onOptionSelected)
        }

        composeTestRule.onNodeWithTag(Tags.TAG_MARKETING_OPTION+0).performClick()
        verify(onOptionSelected).invoke(MarketingOption.ALLOWED)
    }
}