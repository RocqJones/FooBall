package com.zonkesoft.fooball.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zonkesoft.fooball.R
import com.zonkesoft.fooball.domain.state.NetworkState
import com.zonkesoft.fooball.ui.components.TextBold
import com.zonkesoft.fooball.ui.components.TextMedium
import com.zonkesoft.fooball.ui.viewmodel.OfflineViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Screen displayed when the device is offline.
 * Shows connection status and allows user to refresh/retry.
 */
@Composable
fun OfflineScreen(
    viewModel: OfflineViewModel = koinViewModel()
) {
    val networkState by viewModel.networkState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon based on state
            AnimatedVisibility(
                visible = networkState is NetworkState.Checking,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(
                visible = networkState is NetworkState.Disconnected,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(R.string.no_connection),
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            TextBold(
                text = when (networkState) {
                    is NetworkState.Connected -> stringResource(R.string.connected)
                    is NetworkState.Disconnected -> stringResource(R.string.no_internet_connection)
                    is NetworkState.Checking -> stringResource(R.string.checking_connection)
                },
                fontSize = 24.sp,
                color = when (networkState) {
                    is NetworkState.Connected -> MaterialTheme.colorScheme.primary
                    is NetworkState.Disconnected -> MaterialTheme.colorScheme.error
                    is NetworkState.Checking -> MaterialTheme.colorScheme.onBackground
                },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            TextMedium(
                text = when (networkState) {
                    is NetworkState.Connected -> stringResource(R.string.your_device_is_now_connected_to_the_internet)
                    is NetworkState.Disconnected -> stringResource(R.string.please_check_your_internet_connection_and_try_again)
                    is NetworkState.Checking -> stringResource(R.string.please_wait_while_we_check_your_connection)
                },
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Refresh button
            AnimatedVisibility(
                visible = networkState is NetworkState.Disconnected || networkState is NetworkState.Connected,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    onClick = { viewModel.checkNetworkState() },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.refresh),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = stringResource(R.string.retry_connection),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}