package com.example.firebaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.firebaseapp.data.enitity.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatViewModel : ViewModel() {

    private val firestore = Firebase.firestore
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun fetchUsers() {
        firestore.collection("users")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Error fetching users", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val usersList = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
                    _users.value = usersList
                }
            }
    }

    fun createChat(user1Id: String, user2Id: String) {

        val chat = hashMapOf(
            "participants" to listOf(user1Id, user2Id),
            "lastMessage" to "",
            "lastMessageTimestamp" to FieldValue.serverTimestamp()
        )

        Firebase.firestore.collection("chats")
            .add(chat)
            .addOnSuccessListener { documentReference ->
                Log.d("Chat", "Chat created with ID: ${documentReference.id}")
            }
    }

}