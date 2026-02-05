package com.zonkesoft.fooball.domain.state

/**
 * Represents the network connectivity state of the device.
 */
sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
}