package com.zonkesoft.fooball.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.zonkesoft.fooball.core.utils.NetworkConnectivityObserver
import com.zonkesoft.fooball.domain.state.NetworkState
import org.koin.compose.koinInject

/**
 * A composable that observes network connectivity and displays different content
 * based on the connection state.
 *
 * @param networkConnectivityObserver The network observer to use (injected by default)
 * @param onlineContent The content to display when the device is connected to the internet
 * @param offlineContent The content to display when the device is not connected (optional).
 *                       If null, a default OfflineContent screen will be displayed.
 */
@Composable
fun NetworkAwareContent(
    networkConnectivityObserver: NetworkConnectivityObserver = koinInject(),
    onlineContent: @Composable () -> Unit,
    offlineContent: (@Composable () -> Unit)? = null
) {
    val networkState by networkConnectivityObserver.observe().collectAsState(
        initial = NetworkState.Disconnected
    )

    when (networkState) {
        is NetworkState.Connected -> onlineContent()
        is NetworkState.Disconnected -> {
            if (offlineContent != null) {
                offlineContent()
            } else {
                OfflineContent()
            }
        }
    }
}

@Composable
fun OnlineContent(
    networkConnectivityObserver: NetworkConnectivityObserver = koinInject(),
    content: @Composable () -> Unit
) {
    val networkState by networkConnectivityObserver.observe().collectAsState(
        initial = NetworkState.Disconnected
    )

    if (networkState is NetworkState.Connected) {
        content()
    }
}

@Composable
fun isOnline(
    networkConnectivityObserver: NetworkConnectivityObserver = koinInject()
): Boolean {
    val networkState by networkConnectivityObserver.observe().collectAsState(
        initial = NetworkState.Disconnected
    )
    return networkState is NetworkState.Connected
}
