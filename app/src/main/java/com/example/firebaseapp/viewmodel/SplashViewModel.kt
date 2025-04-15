package com.example.firebaseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel (private val pref: UserPreferences) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState




    fun getSession(): LiveData<String?> {
        return pref.getUserId().asLiveData()
    }
}