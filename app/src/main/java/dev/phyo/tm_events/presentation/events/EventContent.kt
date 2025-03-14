package dev.phyo.tm_events.presentation.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EventContent(eventsViewModel: EventsViewModel) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    when(val uiState = uiState){
        is UIState.Loading -> {}
        is UIState.Success -> {
            EventList(uiState.eventList)
        }
        is UIState.Error -> {}
    }
}