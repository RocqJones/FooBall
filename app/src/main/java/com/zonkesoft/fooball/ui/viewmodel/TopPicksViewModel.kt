package com.zonkesoft.fooball.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zonkesoft.fooball.data_source.repository.PredictionsRepository
import com.zonkesoft.fooball.domain.model.TopPick
import com.zonkesoft.fooball.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TopPicksUiState {
    object Loading : TopPicksUiState()
    data class Success(val topPicks: List<TopPick>) : TopPicksUiState()
    data class Error(val message: String) : TopPicksUiState()
}

class TopPicksViewModel(
    private val repository: PredictionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TopPicksUiState>(TopPicksUiState.Loading)
    val uiState: StateFlow<TopPicksUiState> = _uiState.asStateFlow()

    init {
        loadTopPicks()
    }

    fun loadTopPicks() {
        viewModelScope.launch {
            repository.getTopPicks().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> TopPicksUiState.Loading
                    is Result.Success -> TopPicksUiState.Success(result.data)
                    is Result.Error -> TopPicksUiState.Error(
                        result.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    fun retry() {
        loadTopPicks()
    }
}

