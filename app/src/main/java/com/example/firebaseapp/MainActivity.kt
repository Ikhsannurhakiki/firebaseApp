package com.example.firebaseapp

import AuthViewModelFactory
import LoginViewModel
import MainViewModelFactory
import RegisterViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseapp.data.AuthRepository
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.dataStore
import com.example.firebaseapp.navigation.Screen
import com.example.firebaseapp.screen.HomeScreen
import com.example.firebaseapp.screen.LoginScreen
import com.example.firebaseapp.screen.RegisterScreen
import com.example.firebaseapp.viewmodel.ChatViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ikhsannurhakiki.aplikasiforum.utils.AppExecutors

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val appExecutors = remember { AppExecutors() }
    val firestore = remember { FirebaseFirestore.getInstance() }
    val authRepository = remember { AuthRepository.getInstance(FirebaseAuth.getInstance(), firestore) }
    val registerViewModel: RegisterViewModel = viewModel(factory = AuthViewModelFactory(UserPreferences.getInstance(context.dataStore), authRepository))
    val loginViewModel: LoginViewModel = viewModel(factory = AuthViewModelFactory(UserPreferences.getInstance(context.dataStore), authRepository))
    val chatViewModel: ChatViewModel = viewModel(factory = MainViewModelFactory())

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(chatViewModel)
        }

        composable(Screen.Login.route) {
            LoginScreen(loginViewModel, navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(registerViewModel, navController)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            setContent {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}


