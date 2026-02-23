package com.vandemunconnect.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.vandemunconnect.data.model.ChatMessage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val realtimeDatabase: FirebaseDatabase
) {
    suspend fun sendMessage(message: ChatMessage) {
        firestore.collection("messages").add(message.copy(timestamp = Timestamp.now())).await()
    }

    fun sendVoiceSignal(channelId: String, payload: Map<String, Any>) {
        realtimeDatabase.reference.child("voiceSignals").child(channelId).setValue(payload)
    }
}
