package com.zonkesoft.fooball.ui.navigation

sealed class Screens(val route: String) {
    object SplashScreen : Screens("splash_screen")
    object HomeScreen : Screens("home_screen")
    object OfflineScreen : Screens("offline_screen")
}