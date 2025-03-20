package com.example.firebaseapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firebaseapp.viewmodel.ChatViewModel

@Composable
fun HomeScreen(viewModel: ChatViewModel){
    val usersState = viewModel.users.collectAsState()
    viewModel.fetchUsers()

    Scaffold {
        paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            LazyColumn {
                items(usersState.value) { user ->   // ✅ Use .value to extract List<User>
                    Text(text = user.name)
                }
            }
        }
    }

}
