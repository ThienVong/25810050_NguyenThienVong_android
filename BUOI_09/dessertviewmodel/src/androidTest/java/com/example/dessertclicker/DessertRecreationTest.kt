package com.example.dessertclicker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class DessertRecreationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()
    @Test fun revenueAndDessertSurviveRecreation() {
        repeat(5) { compose.onNodeWithContentDescription("Sell dessert").performClick() }
        compose.onNodeWithText("5").assertExists()
        compose.onNodeWithText("$25").assertExists()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("5").assertExists()
        compose.onNodeWithText("$25").assertExists()
        compose.onNodeWithContentDescription("Sell dessert").performClick()
        compose.onNodeWithText("6").assertExists()
        compose.onNodeWithText("$35").assertExists()
    }
}
