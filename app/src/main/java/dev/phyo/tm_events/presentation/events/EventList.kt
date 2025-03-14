package dev.phyo.tm_events.presentation.events

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.phyo.tm_events.data.model.Event

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