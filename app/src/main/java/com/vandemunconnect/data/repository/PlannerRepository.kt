package com.vandemunconnect.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.vandemunconnect.data.model.PlannerReminder
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlannerRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun addReminder(uid: String, reminder: PlannerReminder) {
        firestore.collection("planners").document(uid).collection("reminders")
            .document(reminder.id.ifBlank { firestore.collection("tmp").document().id })
            .set(reminder).await()
    }
}
