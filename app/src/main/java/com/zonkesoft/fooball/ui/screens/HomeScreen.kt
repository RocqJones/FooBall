package com.zonkesoft.fooball.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zonkesoft.fooball.R
import com.zonkesoft.fooball.ui.components.NetworkAwareContent
import com.zonkesoft.fooball.ui.components.OfflineContent
import com.zonkesoft.fooball.ui.components.TextBold
import com.zonkesoft.fooball.ui.components.TextMedium
import com.zonkesoft.fooball.ui.viewmodel.HomeUiState

@Composable
fun HomeScreen(uiState: HomeUiState, navController: NavHostController) {
    NetworkAwareContent(
        onlineContent = { HomeContent(uiState) },
        offlineContent = {
            OfflineContent(
                message = stringResource(R.string.you_re_offline),
                subMessage = stringResource(R.string.most_of_the_features_will_be_limited_reconnect_and_refresh_to_see_the_content)
            )
        },
        loadingContent = { LoadingHomeContent() }
    )
}

@Composable
private fun HomeContent(uiState: HomeUiState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextBold(
                text = "Home Screen",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            TextMedium(
                text = "You are online!",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp)
            )
            // Add your actual home content here
        }
    }
}

@Composable
private fun LoadingHomeContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}