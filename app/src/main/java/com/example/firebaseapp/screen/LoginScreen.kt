package com.example.firebaseapp.screen

import AuthViewModel
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
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firebaseapp.navigation.Screen
import com.example.firebaseapp.widget.CustomTextField

@Composable
fun LoginScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "Email",
            isPassword = false,
            errorMessage = if (state.email.isEmpty()) "Email required" else null,
            leadingIcon = Icons.Default.Email,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "Password",
            isPassword = true,
            errorMessage = if (state.password.length < 6) "Min 6 characters" else null,
            leadingIcon = Icons.Default.Password,
            modifier = Modifier
        )



        TextButton(
            onClick = { viewModel.onForgotPasswordClicked() },
            modifier = Modifier.fillMaxWidth(),
            enabled = true) {
            Text("Forgot Password");
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.login() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Login")
            }
        }

        TextButton(
            onClick = { navController.navigate(Screen.Register.route) },
            modifier = Modifier.fillMaxWidth(),
            enabled = true) {
            Text("Need an account? Register");
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    val fakeNavController = rememberNavController()
    val fakeViewModel = AuthViewModel()
    MaterialTheme {
        LoginScreen(fakeViewModel, fakeNavController)
    }
}