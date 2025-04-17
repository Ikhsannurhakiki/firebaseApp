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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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

    var showAccountCenterDialog by remember { mutableStateOf(false) }
    var showLogOutDialog by remember { mutableStateOf(false) }
    if (showAccountCenterDialog) {
        AccountCenterDialog(
            onDismiss = { showAccountCenterDialog = false },
            onChangePassword = { /* Navigate to Change Password */ },
            onChangeProfileImage = { /* Open image picker */ },
            onPrivacyPolicy = { /* Navigate to Privacy Policy screen */ },
            onLogout = { /* Perform logout */ }
        )
    }

    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.getUserDataFromFirestore(userId) { fetchedUser ->
                user = fetchedUser
            }
        }
    }



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

        SettingsItem(
            icon = Icons.Default.AccountCircle,
            title = "Account Center",
            hasSwitch = false,
            switchChecked = false,
            onSwitchChange = null,
            onClick = { showAccountCenterDialog = true }
        )

        SettingsItem(
            icon = Icons.Default.Language,
            title = "Language",
            hasSwitch = false,
            switchChecked = false,
            onSwitchChange = null,
            onClick = { }
        )

        SettingsItem(
            icon = Icons.Default.DarkMode,
            title = "Dark Mode",
            hasSwitch = true,
            switchChecked = false,
            onSwitchChange = null,
            onClick = { }
        )

        SettingsItem(
            icon = Icons.Default.Notifications,
            title = "Notifications",
            hasSwitch = true,
            switchChecked = false,
            onSwitchChange = null,
            onClick = { }
        )

        SettingsItem(
            icon = Icons.Default.Description,
            title = "Terms & Conditions",
            hasSwitch = false,
            switchChecked = false,
            onSwitchChange = null,
            onClick = { }
        )

        SettingsItem(
            icon = Icons.Default.Lock,
            title = "Privacy Policy",
            hasSwitch = false,
            switchChecked = false,
            onSwitchChange = null,
            onClick = { }
        )

        SettingsItem(
            icon = Icons.Default.Info,
            title = "Help & Support",
            hasSwitch = false,
            switchChecked = false,
            onSwitchChange = null,
            onClick = { }
        )
    }

// Logout Confirmation Dialog
    if (showLogOutDialog) {
        AlertDialog(
            onDismissRequest = { showLogOutDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogOutDialog = false
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Settings.route) { inclusive = true }
                    }
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogOutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    hasSwitch: Boolean = false,
    switchChecked: Boolean = false,
    onSwitchChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    contentDescription: String? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() }
            .padding(horizontal = 0.dp, vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null
        )
    }
}


@Composable
fun AccountCenterDialog(
    onDismiss: () -> Unit,
    onChangePassword: () -> Unit,
    onChangeProfileImage: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onLogout: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Account Center", style = MaterialTheme.typography.titleMedium)
        },
        text = {
            Column {
                AccountCenterItem(
                    icon = Icons.Default.Lock,
                    label = "Change Password",
                    onClick = {
                        onChangePassword()
                        onDismiss()
                    }
                )
                AccountCenterItem(
                    icon = Icons.Default.Person,
                    label = "Change Profile Image",
                    onClick = {
                        onChangeProfileImage()
                        onDismiss()
                    }
                )
                AccountCenterItem(
                    icon = Icons.Default.Info,
                    label = "Privacy Policy",
                    onClick = {
                        onPrivacyPolicy()
                        onDismiss()
                    }
                )
                AccountCenterItem(
                    icon = Icons.Default.Logout,
                    label = "Log Out",
                    onClick = {
                        onLogout()
                        onDismiss()
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun AccountCenterItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}
