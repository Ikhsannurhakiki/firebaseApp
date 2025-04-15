package com.example.firebaseapp.navigation

import com.example.firebaseapp.data.enitity.User

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home/{user}")
    {
        fun createRoute(user: User) = "home/$user"
    }
    object MainScreen: Screen("mainScreen")
    object Chat: Screen("chat")
    object Profile: Screen("profile")
    object Settings: Screen("settings")
}