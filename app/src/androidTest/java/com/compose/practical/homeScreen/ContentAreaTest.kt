package com.compose.practical.homeScreen

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.compose.practical.ui.homeScreen.Tags.TAG_CONTENT_ICON
import com.compose.practical.ui.homeScreen.Tags.TAG_CONTENT_TITLE
import com.compose.practical.ui.homeScreen.components.ContentArea
import com.compose.practical.ui.homeScreen.model.Destinations
import org.junit.Rule
import org.junit.Test

class ContentAreaTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun Destination_Displaye(){

        val destination =  Destinations.Feed

        composeTestRule.setContent {
            ContentArea(destinations = destination)
        }

        composeTestRule.onNodeWithTag(destination.path).assertIsDisplayed()

    }

    @Test
    fun Content_Title_Dispalyed(){
        val destination = Destinations.Contacts

        composeTestRule.setContent {
            ContentArea(destinations = destination)
        }

        composeTestRule.onNodeWithTag(TAG_CONTENT_TITLE).assert(hasText(destination.title))

    }

    @Test
    fun Content_Icon_Dispalyed(){
        val destination = Destinations.Contacts

        composeTestRule.setContent {
            ContentArea(destinations = destination)
        }

        composeTestRule.onNodeWithTag(TAG_CONTENT_ICON).assertContentDescriptionEquals(destination.title)

    }
}