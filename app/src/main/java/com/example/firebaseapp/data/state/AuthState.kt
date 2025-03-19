package com.example.firebaseapp.data.state

data class RegisterState(
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
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorEmailMessage: String? = null,
    val errorPasswordMessage: String? = null,
    val snackbarMessage: String? = null,
)
