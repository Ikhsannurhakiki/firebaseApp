package com.example.firebaseapp.data.state

data class AuthState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorUsernameMessage: String? = null,
    val errorEmailMessage: String? = null,
    val errorPasswordMessage: String? = null,
    val errorConfirmPasswordMessage: String? = null,
    val snackbarMessage: String? = null,
    val errorMessage: String? = null
)
