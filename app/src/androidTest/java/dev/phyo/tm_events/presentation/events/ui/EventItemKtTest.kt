package dev.phyo.tm_events.presentation.events.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import dev.phyo.tm_events.domain.model.Event
import org.junit.Rule
import org.junit.Test

class EventItemKtTest{

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEventItem_displaysEventDetails(){

        // GIVEN
        val event = Event(
            id = 1,
            name = "Sample Event",
            imageUrl = "https://example.com/image.jpg",
            eventDate = "2023-10-15",
            eventTime = "16:00",
            venueName = "Sample Venue",
            city = "New York",
            stateCode = "NY"
        )

        // WHEN
        composeTestRule.setContent {
            EventItem(event)
        }

        // THEN
        composeTestRule.onRoot().printToLog("EvenItemTest")
        composeTestRule.onNodeWithText("Sample Event").assertIsDisplayed()
        composeTestRule.onNodeWithText("2023-10-15").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sample Venue").assertIsDisplayed()
        composeTestRule.onNodeWithText("New York, NY").assertIsDisplayed()
    }
}