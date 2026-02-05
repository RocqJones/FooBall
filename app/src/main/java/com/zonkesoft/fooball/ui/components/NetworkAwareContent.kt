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
 * @param onlineContent The content to display when the device is connected to the internet
 * @param offlineContent The content to display when the device is not connected (optional)
 * @param loadingContent The content to display while checking connectivity (optional)
 */
@Composable
fun NetworkAwareContent(
    networkConnectivityObserver: NetworkConnectivityObserver = koinInject(),
    onlineContent: @Composable () -> Unit,
    offlineContent: (@Composable () -> Unit)? = null,
    loadingContent: (@Composable () -> Unit)? = null
) {
    val networkState by networkConnectivityObserver.observe().collectAsState(
        initial = NetworkState.Checking
    )

    when (networkState) {
        is NetworkState.Connected -> onlineContent()
        is NetworkState.Disconnected -> offlineContent?.invoke()
        is NetworkState.Checking -> loadingContent?.invoke()
    }
}

@Composable
fun OnlineContent(
    networkConnectivityObserver: NetworkConnectivityObserver = koinInject(),
    content: @Composable () -> Unit
) {
    val networkState by networkConnectivityObserver.observe().collectAsState(
        initial = NetworkState.Checking
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
        initial = NetworkState.Checking
    )
    return networkState is NetworkState.Connected
}
