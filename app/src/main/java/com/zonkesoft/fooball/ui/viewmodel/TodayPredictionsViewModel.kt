package com.zonkesoft.fooball.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zonkesoft.fooball.data_source.repository.PredictionsRepository
import com.zonkesoft.fooball.domain.model.Prediction
import com.zonkesoft.fooball.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TodayPredictionsUiState {
    object Loading : TodayPredictionsUiState()
    data class Success(val predictions: List<Prediction>) : TodayPredictionsUiState()
    data class Error(val message: String) : TodayPredictionsUiState()
}

class TodayPredictionsViewModel(
    private val repository: PredictionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodayPredictionsUiState>(TodayPredictionsUiState.Loading)
    val uiState: StateFlow<TodayPredictionsUiState> = _uiState.asStateFlow()

    init {
        loadTodayPredictions()
    }

    fun loadTodayPredictions() {
        viewModelScope.launch {
            repository.getTodayPredictions().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> TodayPredictionsUiState.Loading
                    is Result.Success -> TodayPredictionsUiState.Success(result.data)
                    is Result.Error -> TodayPredictionsUiState.Error(
                        result.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    fun retry() {
        loadTodayPredictions()
    }
}

