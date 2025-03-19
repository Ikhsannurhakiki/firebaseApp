//package com.example.firebaseapp.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.firebaseapp.data.MainRepository
//import com.example.firebaseapp.data.state.AuthState
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class MainViewModel (private val mainRepository: MainRepository) : ViewModel() {
//    private val _uiState = MutableStateFlow(AuthState())
//    val uiState: StateFlow<AuthState> = _uiState
//
//    fun onEmailChange(email: String) {
//        _uiState.value = _uiState.value.copy(email = email)
//    }
//
//    fun onPasswordChange(password: String) {
//        _uiState.value = _uiState.value.copy(password = password)
//    }
//
//    fun onConfirmPasswordChange(password: String) {
//        _uiState.value = _uiState.value.copy(confirmPassword = password)
//    }
//
//    fun onForgotPasswordClicked(){
//
//    }
//
//    fun getSession(){
//
//    }
//
//    fun login() {
//        _uiState.value = _uiState.value.copy(isLoading = true)
//
//        viewModelScope.launch {
//            delay(2000) // Simulate API call
//            _uiState.value = if (_uiState.value.email == "user@example.com" && _uiState.value.password == "password") {
//                _uiState.value.copy(isLoading = false, isSuccess = true)
//            } else {
//                _uiState.value.copy(isLoading = false, errorMessage = "Invalid credentials")
//            }
//        }
//    }
//
//    fun register() {
//        _uiState.value = _uiState.value.copy(isLoading = true)
//        viewModelScope.launch {
//            delay(2000) // Simulate API call
//            _uiState.value = if (_uiState.value.email == "user@example.com" && _uiState.value.password == "password") {
//                _uiState.value.copy(isLoading = false, isSuccess = true)
//            } else {
//                _uiState.value.copy(isLoading = false, errorMessage = "Invalid credentials")
//            }
//        }
//    }
//
//}