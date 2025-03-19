package com.example.firebaseapp.data.remote

data class AuthUser(
    var userId: String?,
    var name: String?,
    var accessRights: String,
    var isLoggedIn: Boolean
)