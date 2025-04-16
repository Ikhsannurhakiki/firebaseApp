package com.example.firebaseapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.firebaseapp.R
import com.example.firebaseapp.data.enitity.User
import com.example.firebaseapp.navigation.Screen
import com.example.firebaseapp.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.getUserDataFromFirestore(userId) { fetchedUser ->
                user = fetchedUser
            }
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Avatar
        Image(
            painter = painterResource(id = R.drawable.ic_avatar_placeholder), // Replace with actual image
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(24.dp))
        //username
        user?.let {
            //username
            Text(text = it.name, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Text(text = it.email, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(40.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account Center",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Account Center")
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = "Account Center")
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
            Icon(
                imageVector = Icons.Default.Language, // Or use another icon
                contentDescription = "Language",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Language")
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = "Change Language")
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
            Icon(
                imageVector = Icons.Default.DarkMode, // Or use another icon
                contentDescription = "Dark Mode",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Dark Mode")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = true,
                onCheckedChange = { }
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
            Icon(
                imageVector = Icons.Default.Notifications, // Or use another icon
                contentDescription = "Notifications",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Notifications")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = true,
                onCheckedChange = { }
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
            Icon(
                imageVector = Icons.Default.Description, // Or use another icon
                contentDescription = "Terms & Conditions",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Terms & Conditions")
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = "Terms & Conditions")
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
            Icon(
                imageVector = Icons.Default.Lock, // Or use another icon
                contentDescription = "Privacy Policy",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Privacy Policy")
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = "Privacy Policy")
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
            Icon(
                imageVector = Icons.Default.Info, // Or use another icon
                contentDescription = "Help & Support",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Help & Support")
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = "Help & Support")
        }

        // Logout Button
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Logout", color = Color.White)
        }
    }

// Logout Confirmation Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Settings.route) { inclusive = true }
                    }
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
