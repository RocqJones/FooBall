package com.zonkesoft.fooball.domain.state

sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
    object Checking : NetworkState()
}