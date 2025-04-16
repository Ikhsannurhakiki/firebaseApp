package com.example.firebaseapp.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.firebaseapp.data.UserPreferences
import com.example.firebaseapp.data.enitity.User
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel(pref: UserPreferences) : ViewModel()  {

    fun getUserDataFromFirestore(userId: String, onResult: (User?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    onResult(user)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting user data", exception)
                onResult(null)
            }
    }

}