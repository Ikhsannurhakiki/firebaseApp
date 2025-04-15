package com.example.firebaseapp.screen

import MainViewModelFactory
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.dataStore
import com.example.firebaseapp.navigation.Screen
import com.example.firebaseapp.viewmodel.ChatViewModel
import com.example.firebaseapp.viewmodel.SettingsViewModel
import com.example.firebaseapp.viewmodel.SettingsViewModelFactory
import com.example.firebaseapp.widget.BottomBar

@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    ) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf("home/{user}", "settings", "profile")
    val context = LocalContext.current
    val chatViewModel: ChatViewModel = viewModel(factory = MainViewModelFactory())
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(
        UserPreferences.getInstance(context.dataStore))
    )
    Scaffold(
        bottomBar = {
            Log.d("current", currentRoute.toString())
            if (showBottomBar) {
                BottomBar(bottomNavController)

            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(chatViewModel, bottomNavController)
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(settingsViewModel, navController)
                }

                composable(Screen.Profile.route) {
                    SettingsScreen(settingsViewModel, navController)
                }
            }
        }
    }
}
