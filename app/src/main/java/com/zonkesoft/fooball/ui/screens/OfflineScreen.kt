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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zonkesoft.fooball.R
import com.zonkesoft.fooball.domain.state.NetworkState
import com.zonkesoft.fooball.ui.components.TextBold
import com.zonkesoft.fooball.ui.components.TextMedium
import com.zonkesoft.fooball.ui.components.TextRegular
import com.zonkesoft.fooball.ui.navigation.Screens
import com.zonkesoft.fooball.ui.viewmodel.OfflineViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Screen displayed when the device is offline.
 * Shows connection status and allows user to refresh/retry.
 * Automatically navigates to HomeScreen when connection is restored.
 */
@Composable
fun OfflineScreen(
    navController: NavHostController,
    viewModel: OfflineViewModel = koinViewModel()
) {
    val networkState by viewModel.networkState.collectAsState()

    // Automatically navigate to HomeScreen when connection is restored
    LaunchedEffect(networkState) {
        if (networkState is NetworkState.Connected) {
            navController.navigate(Screens.HomeScreen.route) {
                popUpTo(Screens.OfflineScreen.route) { inclusive = true }
            }
        }
    }

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

            TextBold(
                text = when (networkState) {
                    is NetworkState.Connected -> stringResource(R.string.connected)
                    is NetworkState.Disconnected -> stringResource(R.string.no_internet_connection)
                },
                fontSize = 24.sp,
                color = when (networkState) {
                    is NetworkState.Connected -> MaterialTheme.colorScheme.primary
                    is NetworkState.Disconnected -> MaterialTheme.colorScheme.error
                },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextMedium(
                text = when (networkState) {
                    is NetworkState.Connected -> stringResource(R.string.your_device_is_now_connected_to_the_internet)
                    is NetworkState.Disconnected -> stringResource(R.string.please_check_your_internet_connection_and_try_again)
                },
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (networkState is NetworkState.Connected) {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(Screens.OfflineScreen.route) { inclusive = true }
                        }
                    } else {
                        viewModel.checkNetworkState()
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                TextRegular(
                    text = if (networkState is NetworkState.Connected) {
                        stringResource(R.string.continue_button)
                    } else {
                        stringResource(R.string.retry_connection)
                    },
                    fontSize = 16.sp
                )
            }
        }
    }
}