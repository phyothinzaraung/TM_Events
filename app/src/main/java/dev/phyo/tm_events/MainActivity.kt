package dev.phyo.tm_events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.phyo.tm_events.presentation.events.EventScreen
import dev.phyo.tm_events.presentation.events.EventsViewModel
import dev.phyo.tm_events.ui.theme.TM_EventsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val eventsViewModel: EventsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TM_EventsTheme {
                EventScreen(eventsViewModel)
            }
        }
    }
}


