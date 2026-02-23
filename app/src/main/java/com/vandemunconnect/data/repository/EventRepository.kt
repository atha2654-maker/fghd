package com.vandemunconnect.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.vandemunconnect.data.model.EventItem
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun addEvent(eventItem: EventItem) {
        firestore.collection("events").document(eventItem.id.ifBlank { firestore.collection("events").document().id })
            .set(eventItem).await()
    }
}
