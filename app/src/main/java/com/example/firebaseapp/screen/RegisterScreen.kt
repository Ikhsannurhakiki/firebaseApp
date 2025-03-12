package com.example.firebaseapp.screen

import AuthViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firebaseapp.widget.CustomTextField

@Composable
fun RegisterScreen(viewModel: AuthViewModel, navHostController: NavHostController) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register Your Account")
        CustomTextField(
            value = state.username,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "username",
            isPassword = false,
            errorMessage = if (state.username.isEmpty()) "Username required" else null,
            leadingIcon = Icons.Default.Person,
            modifier = Modifier,
        )
        CustomTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "email",
            isPassword = false,
            errorMessage = if (state.email.isEmpty()) "Email required" else null,
            leadingIcon = Icons.Default.Email,
            modifier = Modifier,
        )
        CustomTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "password",
            isPassword = true,
            errorMessage = if (state.password.length < 6) "Min 6 characters" else null,
            leadingIcon = Icons.Default.Password,
            modifier = Modifier,
        )
        CustomTextField(
            value = state.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            label = "confirm password",
            isPassword = true,
            errorMessage = if (state.confirmPassword.length < 6) "Min 6 characters" else null,
            leadingIcon = Icons.Default.Password,
            modifier = Modifier,
        )

        ElevatedButton(
            onClick = { viewModel.register() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Sign In")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val fakeNavController = rememberNavController() // Mock NavController
    val fakeViewModel = AuthViewModel()
    MaterialTheme {
        RegisterScreen(fakeViewModel, fakeNavController)
    }
}