package dev.phyo.tm_events.presentation.events.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.util.createErrorPagingData
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class EventListKtTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockEvents = listOf(
        Event(id = 1, name = "Event 1", imageUrl = "", eventDate = "2023-10-15", eventTime = "16:00", venueName = "Venue 1", city = "City 1", stateCode = "ST"),
        Event(id = 2, name = "Event 2", imageUrl = "", eventDate = "2023-10-16", eventTime = "17:00", venueName = "Venue 2", city = "City 2", stateCode = "ST")
    )

    @Test
    fun testEventListRendersCorrectly() {

        // WHEN
        composeTestRule.setContent {
            LazyColumn {
                items(mockEvents) { event ->
                    Text(event.name)
                }
            }
        }

        // THEN
        composeTestRule.onNodeWithText("Event 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Event 2").assertIsDisplayed()
    }

    @Test
    fun testEventList_displaysLoadingState(){
        // GIVEN
        val pagingData = flowOf(PagingData.empty<Event>())

        // WHEN
        composeTestRule.setContent {
            EventList(
                eventList = pagingData.collectAsLazyPagingItems(),
                onSearchQueryChanged = {},
                searchQuery = ""
            )
        }

        // THEN
        composeTestRule.onRoot().printToLog("EventListTest")
        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }

    @Test
    fun testEventList_displaysErrorState() {
        // GIVEN
        val pagingData = createErrorPagingData()

        // WHEN
        composeTestRule.setContent {
            EventList(
                eventList = pagingData.collectAsLazyPagingItems(),
                onSearchQueryChanged = {},
                searchQuery = ""
            )
        }

        // THEN
        composeTestRule.onRoot().printToLog("EventListTest")
        composeTestRule.onNodeWithTag("An error occurred").assertIsDisplayed()
    }

}