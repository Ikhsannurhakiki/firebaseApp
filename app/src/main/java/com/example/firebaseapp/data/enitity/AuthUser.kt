package com.example.firebaseapp.data.enitity

import com.google.firebase.Timestamp

data class AuthUser(
    var userId: String?,
    var name: String?,
    var accessRights: String,
    var isLoggedIn: Boolean
)

data class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val profilePicture: String = "",
    val lastOnline: Timestamp? = null
)
