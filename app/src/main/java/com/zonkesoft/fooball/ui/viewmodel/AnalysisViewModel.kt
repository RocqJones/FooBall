package com.zonkesoft.fooball.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zonkesoft.fooball.data_source.repository.PredictionsRepository
import com.zonkesoft.fooball.domain.model.PredictionsAnalysis
import com.zonkesoft.fooball.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AnalysisUiState {
    object Loading : AnalysisUiState()
    data class Success(val analysis: PredictionsAnalysis) : AnalysisUiState()
    data class Error(val message: String) : AnalysisUiState()
}

class AnalysisViewModel(
    private val repository: PredictionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnalysisUiState>(AnalysisUiState.Loading)
    val uiState: StateFlow<AnalysisUiState> = _uiState.asStateFlow()

    init {
        loadAnalysis()
    }

    fun loadAnalysis() {
        viewModelScope.launch {
            repository.getAnalysis().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> AnalysisUiState.Loading
                    is Result.Success -> AnalysisUiState.Success(result.data)
                    is Result.Error -> AnalysisUiState.Error(
                        result.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    fun retry() {
        loadAnalysis()
    }
}

