package com.example.firebaseapp.data

import android.util.Log
import com.example.firebaseapp.data.enitity.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository private constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun loginUser(email: String, password: String): Result<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID is null")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(username: String, email: String, password: String): Result<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    result.user?.updateProfile(
                        userProfileChangeRequest {
                            displayName = username
                        })?.addOnCompleteListener {
                        saveUserToFirestore(result.user!!)
                    }
                }
            return Result.success("User registered successfully")
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun saveUserToFirestore(user: FirebaseUser) {
        user.let {
            val userData = User(
                userId = it.uid,
                name = it.displayName ?: "Anonymous",
                email = it.email ?: "",
                profilePicture = it.photoUrl?.toString() ?: "",
                lastOnline = Timestamp.now()
            )

            firestore.collection("users")
                .document(it.uid)
                .set(userData)
                .addOnSuccessListener {
                    Log.d("Firestore", "User saved successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error saving user", e)
                }
        }
    }


    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(
            firebaseAuth: FirebaseAuth,
            firestore: FirebaseFirestore,
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: synchronized(this) {
                    instance ?: AuthRepository(firebaseAuth, firestore).apply {
                        instance = this
                    }
                }
            }
    }

}