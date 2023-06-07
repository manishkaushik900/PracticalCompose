package com.compose.practical.authentication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.compose.practical.ui.authentication.PasswordInput
import com.compose.practical.ui.authentication.Tags.TAG_INPUT_PASSWORD
import com.compose.practical.ui.authentication.Tags.TAG_PASSWORD_HIDDEN
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class PasswordInputTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun Password_Displayed() {
        val password = "password123"
        composeTestRule.setContent {
            PasswordInput(
                password = password,
                onPasswordChange = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TAG_INPUT_PASSWORD)
            .assertTextEquals(password)
    }

    @Test
    fun Password_Changes_Triggered() {

        val onPasswordChanged: (password: String) -> Unit = mock()
        val password = "password123"
        composeTestRule.setContent {
            PasswordInput(
                password = password,
                onPasswordChange = onPasswordChanged,
                onDoneClicked = { }
            )
        }

        val passwordText = "456"
        composeTestRule
            .onNodeWithTag(TAG_INPUT_PASSWORD)
            .performTextInput(passwordText)

        verify(onPasswordChanged).invoke(password + passwordText)
    }

    @Test
    fun Password_Toggled_Reflects_state() {
        composeTestRule.setContent {
            PasswordInput(
                password = "password123",
                onPasswordChange = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TAG_PASSWORD_HIDDEN + "true")
            .performClick()

        composeTestRule
            .onNodeWithTag(TAG_PASSWORD_HIDDEN + "false")
            .assertIsDisplayed()

    }


}