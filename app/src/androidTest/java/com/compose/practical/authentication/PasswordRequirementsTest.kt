package com.compose.practical.authentication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.compose.practical.R
import com.compose.practical.ui.authentication.PasswordRequirements
import org.junit.Rule
import org.junit.Test

class PasswordRequirementsTest {

    @get:Rule
    val composeTestRule = createComposeRule()



    @Test
    fun Password_Requirement_Displayed_As_Not_Satisfied() {

        val requirements = PasswordRequirements.values().toList()
        val satisfiedRequirements = requirements[(0 until requirements.count()).random()]

        composeTestRule.setContent {
            PasswordRequirements(satisfiedRequirements = listOf(satisfiedRequirements))
        }

        PasswordRequirements.values().forEach { requirement ->
            val requirement =
                InstrumentationRegistry.getInstrumentation()
                    .targetContext.getString(requirement.label)
            val satisfyRequirement =
                InstrumentationRegistry.getInstrumentation()
                    .targetContext.getString(satisfiedRequirements.label)

            val result = if (requirement == satisfyRequirement) {
                InstrumentationRegistry.getInstrumentation().targetContext.getString(
                    R.string.password_requirement_satisfied,requirement
                )
            } else {
                InstrumentationRegistry.getInstrumentation()
                    .targetContext.getString(
                        R.string.password_requirement_needed,requirement
                    )

            }

            composeTestRule
                .onNodeWithText(result)
                .assertIsDisplayed()
        }
    }

}