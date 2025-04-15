package com.example.firebaseapp.data.enitity

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

data class AuthUser(
    var userId: String?,
    var name: String?,
    var accessRights: String,
    var isLoggedIn: Boolean
)

@Parcelize
data class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val profilePicture: String = "",
    val lastOnline: Timestamp? = null
): Parcelable
