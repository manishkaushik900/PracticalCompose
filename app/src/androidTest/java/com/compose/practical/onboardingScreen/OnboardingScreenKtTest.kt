package com.compose.practical.onboardingScreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.compose.practical.ui.onboardingScreen.OnBoardNavButton
import com.compose.practical.ui.onboardingScreen.OnboardScreen
import com.compose.practical.ui.onboardingScreen.Tags
import com.compose.practical.ui.onboardingScreen.Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON
import com.compose.practical.ui.onboardingScreen.onboardPagesList
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

class OnboardingScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun  assert_onboardScreen_DisplayedCorrectly(){
        composeTestRule.setContent {
            MaterialTheme{
                Surface {
                    OnboardScreen()
                }
            }
        }

        composeTestRule.onNodeWithTag(
            Tags.TAG_ONBOARD_SCREEN
        ).assertIsDisplayed()


    }

    @Test
    fun asser_ONBOARD_SCREEN_IMAGEVIEW_Displayed_Correctly(){
        composeTestRule.setContent {
            MaterialTheme{
                Surface {
                    OnboardScreen()
                }
            }
        }

        composeTestRule.onNodeWithTag(
            Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW
        ).assertIsDisplayed()
    }

    @Test
    fun assert_ONBOARD_SCREEN_Details_Displayed_Correctly(){
        composeTestRule.setContent {
            MaterialTheme{
                Surface {
                    OnboardScreen()
                }
            }
        }

        composeTestRule.onNodeWithText(
            onboardPagesList[0].title
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText(
            onboardPagesList[0].description
        ).assertIsDisplayed()
    }

    @Test
    fun onNextButtonClicked_CallbackTriggered(){
        val onNextButtonClick: ()-> Unit = mock()

        composeTestRule.setContent {
            OnBoardNavButton(
                currentPage = 0,
                noOfPages = 3,
                onNextClicked = onNextButtonClick
            )
        }

        composeTestRule.onNodeWithTag(TAG_ONBOARD_SCREEN_NAV_BUTTON).performClick()

        verify(onNextButtonClick).invoke()

    }

    @Test
    fun onNextButtonClicked_LastPageHandledProperly(){
        val onNextButtonClick: ()-> Unit = mock()

        composeTestRule.setContent {
            OnBoardNavButton(
                currentPage = 2,
                noOfPages = 3,
                onNextClicked = onNextButtonClick
            )
        }

        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()

        composeTestRule.onNodeWithTag(TAG_ONBOARD_SCREEN_NAV_BUTTON).performClick()

        verify(onNextButtonClick, never()).invoke()

    }


}