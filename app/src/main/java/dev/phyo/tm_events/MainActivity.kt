package dev.phyo.tm_events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.presentation.events.EventsViewModel
import dev.phyo.tm_events.presentation.events.UIState
import dev.phyo.tm_events.ui.theme.TM_EventsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val eventsViewModel: EventsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TM_EventsTheme {
                EventContent(eventsViewModel)
            }
        }
    }
}

@Composable
fun EventContent(eventsViewModel: EventsViewModel, modifier: Modifier = Modifier) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    when(val uiState = uiState){
        is UIState.Loading -> {}
        is UIState.Success -> {
            EventList(uiState.eventList)
        }
        is UIState.Error -> {}
    }
}

@Composable
fun EventList(eventList: List<Event>, modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        LazyColumn(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            items(eventList){
                EventItem(it)
            }
        }
    }
}

@Composable
fun EventItem(event: Event, modifier: Modifier = Modifier) {
    Row {
        Text(event.name)
    }
}
