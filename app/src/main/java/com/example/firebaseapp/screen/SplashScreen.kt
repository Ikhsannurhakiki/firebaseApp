package com.example.firebaseapp.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.firebaseapp.navigation.Screen
import com.example.firebaseapp.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel: SplashViewModel, navController: NavHostController
) {
    val state by viewModel.uiState.collectAsState()

    val isLoggedIn by viewModel.getSession().observeAsState(false)

    LaunchedEffect(isLoggedIn) {
        delay(1500) // optional splash delay
        if (isLoggedIn!=null) {
            navController.navigate(Screen.MainScreen.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    Scaffold {
        paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

//@Preview
//@Composable
//fun SplashScreenPreview() {
//    val fakePrefs = FakeUserPreferences()
//    val viewModel = AuthViewModel(fakePrefs)
//    SplashScreen(viewModel, navController)
//}