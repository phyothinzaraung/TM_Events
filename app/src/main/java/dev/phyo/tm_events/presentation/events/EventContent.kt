package dev.phyo.tm_events.presentation.events

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun EventContent(eventsViewModel: EventsViewModel) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is UIState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        is UIState.Success -> {
            val lazyPagingItems = currentState.eventList.collectAsLazyPagingItems()
            EventList(lazyPagingItems)
        }
        is UIState.Error -> {
            Text(
                text = currentState.message,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}