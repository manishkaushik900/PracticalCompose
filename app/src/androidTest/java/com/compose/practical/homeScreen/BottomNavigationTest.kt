package com.compose.practical.homeScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.compose.practical.R
import com.compose.practical.ui.homeScreen.Tags.TAG_BOTTOM_NAVIGATION
import com.compose.practical.ui.homeScreen.components.BottomNavigationBar
import com.compose.practical.ui.homeScreen.model.Destinations
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class BottomNavigationTest {

    @get:Rule
    val composeTestRule =  createComposeRule()

    @Test
    fun Bottom_Navigation_Displayed(){

        composeTestRule.setContent {
            BottomNavigationBar(currentDestination = Destinations.Feed, onNavigate ={} , onFloatingBtnClick = {})
        }

        composeTestRule.onNodeWithTag(TAG_BOTTOM_NAVIGATION).assertIsDisplayed()
    }

    @Test
    fun Bottom_Navigation_Items_Displayed(){
        composeTestRule.setContent {
            BottomNavigationBar(currentDestination = Destinations.Feed, onNavigate ={} , onFloatingBtnClick = {})
        }

        listOf( Destinations.Feed,
            Destinations.Contacts,
            Destinations.Calendar).forEach{
                composeTestRule.onNodeWithText(it.title).assertIsDisplayed()
        }
    }

    @Test
    fun Bottom_Navigatiopn_onNavigate_Callback_Triggered(){

        val destination = Destinations.Contacts
        val onNavigate: (destination:Destinations)-> Unit = mock()

        composeTestRule.setContent {
            BottomNavigationBar(currentDestination = Destinations.Feed, onNavigate =onNavigate , onFloatingBtnClick = {})
        }

        composeTestRule.onNodeWithText(destination.title).performClick()

        verify(onNavigate).invoke(destination)

    }

    @Test
    fun Bottom_Navigatiopn_Displayed(){
        composeTestRule.setContent {
            BottomNavigationBar(currentDestination = Destinations.Feed, onNavigate ={} , onFloatingBtnClick = {})
        }

        composeTestRule.onNodeWithContentDescription( InstrumentationRegistry.getInstrumentation()
            .targetContext.getString(
                R.string.cd_create_item
            )).assertIsDisplayed()

    }

    @Test
    fun Bottom_Navigatiopn_onFloatingBtn_Callback_Triggered(){

        val onFloatingBtn: ()-> Unit = mock()

        composeTestRule.setContent {
            BottomNavigationBar(currentDestination = Destinations.Feed, onNavigate ={} , onFloatingBtnClick = onFloatingBtn)
        }

        composeTestRule.onNodeWithContentDescription( InstrumentationRegistry.getInstrumentation()
            .targetContext.getString(
                R.string.cd_create_item
            )).performClick()

        verify(onFloatingBtn).invoke()

    }

    @Test
    fun Current_Destination_Selected(){
        val destination = Destinations.Contacts
        composeTestRule.setContent {
            BottomNavigationBar(currentDestination = destination, onNavigate ={} , onFloatingBtnClick = {})
        }

        composeTestRule.onNodeWithText(destination.title).assertIsSelected()
        composeTestRule.onNodeWithText(Destinations.Feed.title).assertIsNotSelected()
        composeTestRule.onNodeWithText(Destinations.Calendar.title).assertIsNotSelected()



    }

}