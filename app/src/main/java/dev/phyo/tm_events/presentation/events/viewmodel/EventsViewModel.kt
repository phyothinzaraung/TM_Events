package dev.phyo.tm_events.presentation.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.usecase.GetEventsUseCase
import dev.phyo.tm_events.util.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> get() = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _isOffline = MutableStateFlow(!networkUtils.isOnline())
    val isOffline: StateFlow<Boolean> get() = _isOffline

    init {
        _uiState.value = UIState.Loading
        checkNetworkStatus()
    }


    fun getEvents(query: String = "") {
        viewModelScope.launch {
            UIState.Loading
            delay(500)
            try {
                _searchQuery.value = query
                val events = getEventsUseCase(query)
                _uiState.value = UIState.Success(events)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    private fun checkNetworkStatus(){
        viewModelScope.launch {
            while (true){
                _isOffline.value = !networkUtils.isOnline()
                delay(5000)
            }
        }
    }
}

sealed interface UIState {
    data object Loading : UIState
    data class Success(val data: Flow<PagingData<Event>>) : UIState
    data class Error(val message: String) : UIState
}