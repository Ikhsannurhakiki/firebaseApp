package com.example.firebaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatViewModel: ViewModel()  {
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