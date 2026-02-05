package com.zonkesoft.fooball.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zonkesoft.fooball.ui.screens.HomeScreen
import com.zonkesoft.fooball.ui.screens.OfflineScreen
import com.zonkesoft.fooball.ui.screens.SplashScreen
import com.zonkesoft.fooball.ui.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    homeViewModel: HomeViewModel = koinViewModel()
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(
                navController = navController,
            )
        }

        composable(Screens.HomeScreen.route) {
            val uiState by homeViewModel.uiState.collectAsState()
            HomeScreen(
                uiState = uiState,
                navController = navController,
            )
        }

        composable(Screens.OfflineScreen.route) {
            OfflineScreen()
        }
    }
}