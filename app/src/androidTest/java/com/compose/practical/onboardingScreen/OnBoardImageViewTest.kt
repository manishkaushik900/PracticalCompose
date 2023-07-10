package com.compose.practical.onboardingScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.compose.practical.ui.onboardingScreen.OnBoardImageView
import com.compose.practical.ui.onboardingScreen.Tags
import com.compose.practical.ui.onboardingScreen.onboardPagesList
import org.junit.Rule
import org.junit.Test

class OnBoardImageViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun correctImageDisplayed() {
        val currentPage = onboardPagesList[0]

        composeTestRule.setContent {
                    OnBoardImageView(
                        currentPage = currentPage
                    )
        }

        // Verify that the correct image is displayed
        composeTestRule.onNodeWithTag(Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW+currentPage.title).assertIsDisplayed()

    }
}
