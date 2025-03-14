package com.example.firebaseapp.data

data class AuthUser(
    var userId: String?,
    var name: String?,
    var accessRights: String,
    var isLoggedIn: Boolean
)