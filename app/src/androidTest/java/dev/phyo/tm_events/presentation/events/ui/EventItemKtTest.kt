package dev.phyo.tm_events.presentation.events.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import dev.phyo.tm_events.domain.model.Event
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.onNodeWithTag

@OptIn(ExperimentalGlideComposeApi::class)
class EventItemKtTest{

    @get:Rule
    val composeTestRule = createComposeRule()


    @Composable
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

        GlideImage(
            model = event.imageUrl,
            contentDescription = event.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(8.dp))
                .testTag("eventImage")
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
        composeTestRule.onNodeWithTag("eventImage").assertExists()
    }
}