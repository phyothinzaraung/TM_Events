package dev.phyo.tm_events.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.domain.usecase.GetEventsUseCase
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(getEventsUseCase: GetEventsUseCase): ViewModel() {

    val uiState = getEventsUseCase(city= "Sacramento").map {
        when(it){
            is DataResult.Success -> {UIState.Success(it.data ?: emptyList())}
            is DataResult.Error -> {UIState.Error(it.message ?: "Error Occurs!")}
            is DataResult.Loading -> {UIState.Loading}
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        UIState.Loading
    )
}

sealed interface UIState{
    data object Loading: UIState
    data class Success(val eventList: List<Event>): UIState
    data class Error(val message: String): UIState
}