package com.example.firebaseapp.screen

import AuthViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.dataStore
import com.example.firebaseapp.viewmodel.MainViewModel
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavHostController) {

    Column {
        Text("Hallo")
    }
}