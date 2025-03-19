package com.example.firebaseapp.screen

import RegisterViewModel
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.firebaseapp.widget.CustomTextField

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navHostController: NavHostController) {
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
            onValueChange = { viewModel.onUsernameChange(it) },
            label = "username",
            isPassword = false,
            errorMessage = state.errorUsernameMessage,
            leadingIcon = Icons.Default.Person,
            modifier = Modifier,
        )
        CustomTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "email",
            isPassword = false,
            errorMessage = state.errorEmailMessage,
            leadingIcon = Icons.Default.Email,
            modifier = Modifier,
        )
        CustomTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "password",
            isPassword = true,
            errorMessage = state.errorPasswordMessage,
            leadingIcon = Icons.Default.Password,
            modifier = Modifier,
        )
        CustomTextField(
            value = state.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            label = "confirm password",
            isPassword = true,
            errorMessage = state.errorConfirmPasswordMessage,
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
//    val fakeNavController = rememberNavController() // Mock NavController
//    val fakeViewModel = AuthViewModel()
//    MaterialTheme {
//        RegisterScreen(fakeViewModel, fakeNavController)
//    }
}