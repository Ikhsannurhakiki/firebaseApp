package com.example.firebaseapp

import AuthViewModel
import AuthViewModelFactory
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseapp.data.AuthRepository
import com.example.firebaseapp.data.MainRepository
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.dataStore
import com.example.firebaseapp.navigation.Screen
import com.example.firebaseapp.screen.HomeScreen
import com.example.firebaseapp.screen.LoginScreen
import com.example.firebaseapp.screen.RegisterScreen
import com.example.firebaseapp.ui.theme.FirebaseAppTheme
import com.example.firebaseapp.viewmodel.MainViewModel
import com.example.firebaseapp.viewmodel.MainViewModelFactory
import com.example.firebaseapp.widget.CustomTextField
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.ikhsannurhakiki.aplikasiforum.utils.AppExecutors

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val appExecutors = remember { AppExecutors() }
    val repository = remember { MainRepository.getInstance(appExecutors) }
    val authRepository = remember { AuthRepository.getInstance(FirebaseAuth.getInstance(), appExecutors) }
    val mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(repository))
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(UserPreferences.getInstance(context.dataStore), authRepository))
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(mainViewModel, navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(authViewModel, navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(authViewModel, navController)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}


