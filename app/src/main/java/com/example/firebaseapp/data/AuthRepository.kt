package com.example.firebaseapp.data

import com.google.firebase.auth.FirebaseAuth
import com.ikhsannurhakiki.aplikasiforum.utils.AppExecutors
import kotlinx.coroutines.tasks.await

class AuthRepository private constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appExecutors: AppExecutors
) : MainDataSource {

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(
            firebaseAuth: FirebaseAuth,
            appExecutors: AppExecutors
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(
                    firebaseAuth,
                    appExecutors
                ).apply { instance = this }
            }
    }

    suspend fun registerUser(username: String, email: String, password: String):Result<String> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}