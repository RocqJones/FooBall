package com.zonkesoft.fooball.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Error(val message: String, val isOnline: Boolean) : HomeUiState()
}