package com.example.firebaseapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseapp.data.UserPreferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: UserPreferences) : ViewModel() {

//    val darkMode = pref.darkModeFlow.asLiveData()
//    val notifications = pref.notificationsFlow.asLiveData()
//
//    fun toggleDarkMode(enabled: Boolean) {
//        viewModelScope.launch { pref.setDarkMode(enabled) }
//    }
//
//    fun toggleNotifications(enabled: Boolean) {
//        viewModelScope.launch { pref.setNotifications(enabled) }
//    }

    fun logout() {
        viewModelScope.launch {
            pref.deleteUserId() // clear login token or UID
            FirebaseAuth.getInstance().signOut()
        }

    }
}
