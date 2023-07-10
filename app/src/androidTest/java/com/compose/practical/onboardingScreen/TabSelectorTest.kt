package com.compose.practical.onboardingScreen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.compose.practical.ui.onboardingScreen.TabSelector
import com.compose.practical.ui.onboardingScreen.Tags.TAG_ONBOARD_TAG_ROW
import com.compose.practical.ui.onboardingScreen.onboardPagesList
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TabSelectorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun correctNumberOfTabsDisplayed() {
        val currentPage = 0
        composeTestRule.setContent {
                    TabSelector(
                        onboardPages = onboardPagesList,
                        currentPage = currentPage,
                        onTabSelected = {}
                    )
        }

        // Verify that the correct number of tabs are displayed
        composeTestRule.onNodeWithTag(TAG_ONBOARD_TAG_ROW)
            .onChildren()
            .assertCountEquals(onboardPagesList.size)
    }

    @Test
    fun validateSelectedTabBasedOnCurrentPage() {
        val currentPage = 2
        val onTabSelected: (Int) -> Unit = {}

        composeTestRule.setContent {
                    TabSelector(
                        onboardPages = onboardPagesList,
                        currentPage = currentPage,
                        onTabSelected = onTabSelected
                    )
        }

        composeTestRule.onNodeWithTag(TAG_ONBOARD_TAG_ROW).onChildren()[currentPage].assertIsSelected()
    }

    @Test
    fun tabSelection_onTabSelectedCallbackTriggered() {
        var selectedTabIndex = -1
        composeTestRule.setContent {

            TabSelector(
                onboardPages = onboardPagesList,
                currentPage = 0,
                onTabSelected = { selectedTabIndex = it }
            )

        }

        // Click on the second tab
        composeTestRule.onNodeWithTag(TAG_ONBOARD_TAG_ROW).onChildren()[0].assertIsSelected()
        composeTestRule.onNodeWithTag(TAG_ONBOARD_TAG_ROW).onChildren()[1].performClick()

        // Verify that the onTabSelected callback is triggered with the correct index
        assertEquals(1, selectedTabIndex)
    }
}

