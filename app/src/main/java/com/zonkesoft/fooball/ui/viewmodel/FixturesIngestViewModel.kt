package com.zonkesoft.fooball.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zonkesoft.fooball.data_source.repository.PredictionsRepository
import com.zonkesoft.fooball.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class FixturesIngestUiState {
    object Idle : FixturesIngestUiState()
    object Loading : FixturesIngestUiState()
    data class Success(val message: String) : FixturesIngestUiState()
    data class Error(val message: String) : FixturesIngestUiState()
}

class FixturesIngestViewModel(
    private val repository: PredictionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FixturesIngestUiState>(FixturesIngestUiState.Idle)
    val uiState: StateFlow<FixturesIngestUiState> = _uiState.asStateFlow()

    fun ingestFixtures() {
        viewModelScope.launch {
            repository.ingestFixtures().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> FixturesIngestUiState.Loading
                    is Result.Success -> FixturesIngestUiState.Success(result.data)
                    is Result.Error -> FixturesIngestUiState.Error(
                        result.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = FixturesIngestUiState.Idle
    }
}

