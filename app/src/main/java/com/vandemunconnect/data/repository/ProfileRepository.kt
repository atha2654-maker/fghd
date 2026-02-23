package com.vandemunconnect.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.vandemunconnect.data.model.UserProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    suspend fun saveProfile(profile: UserProfile) {
        firestore.collection("profiles").document(profile.uid).set(profile).await()
    }

    suspend fun loadProfile(uid: String): UserProfile? {
        return firestore.collection("profiles").document(uid).get().await().toObject(UserProfile::class.java)
    }

    suspend fun uploadProfilePhoto(uid: String, uri: Uri): String {
        val ref = storage.reference.child("profiles/$uid/avatar.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }
}
