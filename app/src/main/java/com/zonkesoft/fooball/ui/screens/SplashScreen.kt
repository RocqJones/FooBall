package com.zonkesoft.fooball.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zonkesoft.fooball.R
import com.zonkesoft.fooball.ui.components.CompactOfflineContent
import com.zonkesoft.fooball.ui.components.NetworkAwareContent
import com.zonkesoft.fooball.ui.components.TextMedium
import com.zonkesoft.fooball.ui.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var splashDelayComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000)
        splashDelayComplete = true
    }

    NetworkAwareContent(
        onlineContent = {
            LaunchedEffect(splashDelayComplete) {
                if (splashDelayComplete) {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.SplashScreen.route) { inclusive = true }
                    }
                }
            }
            SplashContent()
        },
        offlineContent = {
            LaunchedEffect(splashDelayComplete) {
                if (splashDelayComplete) {
                    navController.navigate(Screens.OfflineScreen.route) {
                        popUpTo(Screens.SplashScreen.route) { inclusive = true }
                    }
                }
            }
            SplashContent(isOffline = true)
        }
    )
}

@Composable
private fun SplashContent(isOffline: Boolean = false) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(200.dp),
            )

            if (isOffline) {
                Spacer(modifier = Modifier.height(24.dp))
                CompactOfflineContent()
            }
        }

        TextMedium(
            text = stringResource(R.string.insights_for_smarter_decisions),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
        )
    }
}