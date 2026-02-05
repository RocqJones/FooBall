package com.zonkesoft.fooball.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zonkesoft.fooball.core.utils.NetworkConnectivityObserver
import com.zonkesoft.fooball.domain.state.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for handling offline/online state.
 */
class OfflineViewModel(
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Disconnected)
    val networkState: StateFlow<NetworkState> = _networkState.asStateFlow()

    init {
        observeNetworkState()
    }

    /**
     * Observes network connectivity changes.
     */
    private fun observeNetworkState() {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { state ->
                _networkState.value = state
            }
        }
    }

    /**
     * Manually checks the current network state.
     * Useful for refresh functionality.
     */
    fun checkNetworkState() {
        val isConnected = networkConnectivityObserver.isConnected()
        _networkState.value = if (isConnected) {
            NetworkState.Connected
        } else {
            NetworkState.Disconnected
        }
    }
}
