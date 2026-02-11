package com.zonkesoft.fooball.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zonkesoft.fooball.data_source.repository.CompetitionsRepository
import com.zonkesoft.fooball.domain.model.Competition
import com.zonkesoft.fooball.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val competitions: List<Competition>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(
    private val repository: CompetitionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCompetitions()
    }

    fun loadCompetitions() {
        viewModelScope.launch {
            repository.getCompetitions().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> HomeUiState.Loading
                    is Result.Success -> HomeUiState.Success(result.data)
                    is Result.Error -> HomeUiState.Error(
                        result.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    fun retry() {
        loadCompetitions()
    }
}