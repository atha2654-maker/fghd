package com.vandemunconnect.data.model

import com.google.firebase.Timestamp

data class UserProfile(
    val uid: String = "",
    val photoUrl: String = "",
    val name: String = "",
    val portfolio: String = "",
    val committee: String = "",
    val contact: String = "",
    val bio: String = "",
    val whatsapp: String = "",
    val isAdmin: Boolean = false,
    val isOwner: Boolean = false
)

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String? = null,
    val roomId: String = "public",
    val text: String = "",
    val voiceUrl: String = "",
    val timestamp: Timestamp = Timestamp.now()
)

data class EventItem(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val date: String = "",
    val fee: String = "",
    val enrollmentLink: String = "",
    val prize: String = "",
    val isPastEvent: Boolean = false
)

data class PlannerReminder(
    val id: String = "",
    val title: String = "",
    val dateEpoch: Long = 0L,
    val note: String = ""
)
