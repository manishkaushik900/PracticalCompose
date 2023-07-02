package com.compose.practical.homeScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.compose.practical.ui.homeScreen.components.Navigation
import com.compose.practical.ui.homeScreen.model.Destinations
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun Feed_Displayed_ByDefault(){
        composeTestRule.setContent {
            Navigation(navController = rememberNavController())
        }

        composeTestRule.onNodeWithTag(Destinations.Feed.path)
            .assertIsDisplayed()
    }

    @Test
    fun Contacts_Displayed(){

        assertNavigation(Destinations.Contacts)

    }
    @Test
    fun Calendar_Displayed(){
        assertNavigation(Destinations.Calendar)
    }

    @Test
    fun Create_Displayed(){
        assertNavigation(Destinations.Add)

    }

    @Test
    fun Upgrade_Displayed(){
        assertNavigation(Destinations.Upgrade)
    }

    @Test
    fun Settings_Displayed(){
        assertNavigation(Destinations.Settings)
    }

    private fun assertNavigation(destinations: Destinations){

        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(navController = navController)
            navController.navigate(destinations.path)

        }
            composeTestRule.onNodeWithTag(destinations.path).assertIsDisplayed()

    }
}