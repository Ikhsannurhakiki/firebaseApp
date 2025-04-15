package com.example.firebaseapp.navigation

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)