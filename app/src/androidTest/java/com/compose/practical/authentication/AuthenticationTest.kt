package com.compose.practical.authentication

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.compose.practical.R
import com.compose.practical.ui.authentication.Authentication
import com.compose.practical.ui.authentication.AuthenticationContent
import com.compose.practical.ui.authentication.AuthenticationState
import com.compose.practical.ui.authentication.Tags
import com.compose.practical.ui.authentication.Tags.TAG_AUTHENTICATE_BUTTON
import com.compose.practical.ui.authentication.Tags.TAG_CONTENT
import com.compose.practical.ui.authentication.Tags.TAG_ERROR_ALERT
import com.compose.practical.ui.authentication.Tags.TAG_INPUT_EMAIL
import com.compose.practical.ui.authentication.Tags.TAG_INPUT_PASSWORD
import com.compose.practical.ui.authentication.Tags.TAG_PROGRESS
import org.junit.Rule
import org.junit.Test

class AuthenticationTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun Sign_In_Title_Is_Displayed_By_Default() {

        composeTestRule.setContent {
            Authentication()
        }

        composeTestRule.onNodeWithText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(
                R.string.label_sign_in_to_account
            )
        ).assertIsDisplayed()

    }

    @Test
    fun Need_Account_Displayed_By_Default() {
        composeTestRule.setContent {
            Authentication()
        }
        composeTestRule
            .onNodeWithText(
                InstrumentationRegistry.getInstrumentation()
                    .targetContext.getString(
                        R.string.action_need_account
                    )
            )
            .assertIsDisplayed()
    }

    @Test
    fun Sign_Up_Title_Is_Displayed_After_Toggled() {

        composeTestRule.setContent {
            Authentication()
        }

        composeTestRule
            .onNodeWithText(
                InstrumentationRegistry.getInstrumentation()
                    .targetContext.getString(
                        R.string.action_need_account
                    )
            ).performClick()

        composeTestRule.onNodeWithText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(
                R.string.label_sign_up_for_account
            )
        ).assertIsDisplayed()

    }

    @Test
    fun Already_Account_Is_Displayed_After_Toggled() {

        composeTestRule.setContent {
            Authentication()
        }

        composeTestRule
            .onNodeWithTag(
                Tags.TAG_AUTHENTICATION_TOGGLE
            ).performClick()

        composeTestRule.onNodeWithText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(
                R.string.action_already_have_account
            )
        ).assertIsDisplayed()

    }

    @Test
    fun Sign_Up_Button_Displayed_After_Toggle() {
        composeTestRule.setContent {
            Authentication()
        }
        composeTestRule
            .onNodeWithTag(
                Tags.TAG_AUTHENTICATION_TOGGLE
            ).performClick()

        composeTestRule.onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).assertTextEquals(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(
                R.string.action_sign_up
            )
        )
    }

    @Test
    fun Authentication_Button_Disabled_By_Default() {
        composeTestRule.setContent {
            Authentication()
        }
        composeTestRule
                .onNodeWithTag(TAG_AUTHENTICATE_BUTTON)
            .assertIsNotEnabled()
    }

    @Test
    fun Authentication_Button_Enabled_With_Valid_Content() {
        composeTestRule.setContent {
            Authentication()
        }

        composeTestRule.onNodeWithTag(TAG_INPUT_EMAIL).performTextInput("contacr@manish.com")

        composeTestRule.onNodeWithTag(TAG_INPUT_PASSWORD).performTextInput("password")

        composeTestRule.onNodeWithTag(
            TAG_AUTHENTICATE_BUTTON
        ).assertIsEnabled()

    }


    @Test
    fun Authentication_Button_Enabled_Then_Disabled_With_Valid_Content() {
        composeTestRule.setContent {
            Authentication()
        }

        composeTestRule.onNodeWithTag(TAG_INPUT_EMAIL).performTextInput("contacr@manish.com")

        composeTestRule.onNodeWithTag(TAG_INPUT_PASSWORD).performTextInput("password")

        composeTestRule.onNodeWithTag(
            TAG_AUTHENTICATE_BUTTON
        ).assertIsEnabled()

        composeTestRule.onNodeWithTag(TAG_INPUT_EMAIL).performTextClearance()

        composeTestRule.onNodeWithTag(TAG_INPUT_PASSWORD).performTextClearance()

        composeTestRule
            .onNodeWithTag(TAG_AUTHENTICATE_BUTTON)
            .assertIsNotEnabled()

    }

    @Test
    fun Error_Alert_Not_Dispalyed_By_Default(){
        composeTestRule.setContent {
            Authentication()
        }

        composeTestRule
            .onNodeWithTag(TAG_ERROR_ALERT).assertDoesNotExist()

    }

    @Test
    fun Error_Alert_Dispalyed_After_Error(){
        composeTestRule.setContent {
            AuthenticationContent(
                modifier= Modifier,
                AuthenticationState(
                    error = "Some error"
                ),
                handleEvent = {}
            )
        }
        composeTestRule.onNodeWithTag(
            TAG_ERROR_ALERT
        ).assertIsDisplayed()

    }

    @Test
    fun Progress_Not_Displayed_By_Default() {
        composeTestRule.setContent {
            Authentication()
        }
        composeTestRule.onNodeWithTag(
            TAG_PROGRESS
        ).assertDoesNotExist()
    }

    @Test
    fun Progress_Displayed_While_Loading() {

        composeTestRule.setContent {
            AuthenticationContent(
                modifier= Modifier,
                AuthenticationState(isLoading = true),
                handleEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(
            TAG_PROGRESS
        ).assertIsDisplayed()
    }

    @Test
    fun Progress_Not_Displayed_After_Loading() {
        composeTestRule.setContent {
            AuthenticationContent(
                authenticationState = AuthenticationState(
                    email = "contact@compose.academy",
                    password = "password"
                )
            ) { }
        }
        composeTestRule.onNodeWithTag(
            TAG_AUTHENTICATE_BUTTON
        ).performClick()

        composeTestRule.onNodeWithTag(
             TAG_PROGRESS
        ).assertDoesNotExist()
    }

    @Test
    fun Content_Displayed_After_Loading() {
        composeTestRule.setContent {
            Authentication()
        }
        composeTestRule.onNodeWithTag(
            TAG_INPUT_EMAIL
        ).performTextInput("contact@compose.academy")
        composeTestRule.onNodeWithTag(
            TAG_INPUT_PASSWORD
        ).performTextInput("password")
        composeTestRule.onNodeWithTag(
            TAG_AUTHENTICATE_BUTTON
        ).performClick()
        composeTestRule.onNodeWithTag(
            TAG_CONTENT, useUnmergedTree = true
        ).assertExists()
    }

}