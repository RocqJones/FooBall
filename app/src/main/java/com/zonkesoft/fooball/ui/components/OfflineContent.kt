package com.zonkesoft.fooball.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zonkesoft.fooball.R

/**
 * Common shared offline content component that can be used across all screens.
 * Displays a consistent offline message with icon when the device is disconnected.
 *
 * @param modifier Optional modifier for the root container
 * @param message Optional custom message to display (defaults to standard offline message)
 * @param showIcon Whether to show the offline cloud icon (default: true)
 */
@Composable
fun OfflineContent(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.no_internet_connection),
    subMessage: String = stringResource(R.string.please_check_your_internet_connection_and_try_again),
    showIcon: Boolean = true
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showIcon) {
                Icon(
                    imageVector = Icons.Default.CloudOff,
                    contentDescription = stringResource(R.string.no_internet_connection),
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            TextBold(
                text = message,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextMedium(
                text = subMessage,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

/**
 * Compact version of offline content for smaller spaces like cards or sections.
 *
 * @param modifier Optional modifier for the root container
 * @param message Optional custom message to display
 */
@Composable
fun CompactOfflineContent(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.offline_check_your_connection)
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CloudOff,
            contentDescription = stringResource(R.string.no_internet_connection),
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextMedium(
            text = message,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}
