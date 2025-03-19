package com.example.firebaseapp.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository private constructor(
    private val firebaseAuth: FirebaseAuth
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
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(
            firebaseAuth: FirebaseAuth,
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: synchronized(this) {
                    instance ?: AuthRepository(firebaseAuth).apply {
                        instance = this
                    }
                }
            }
    }

}