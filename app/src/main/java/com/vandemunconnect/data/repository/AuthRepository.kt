package com.vandemunconnect.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    fun currentUserId(): String? = auth.currentUser?.uid

    suspend fun signInWithPhoneCredential(credential: PhoneAuthCredential): Result<String> = runCatching {
        auth.signInWithCredential(credential).await().user?.uid.orEmpty()
    }

    suspend fun signInWithEmailCode(email: String, code: String): Result<String> = runCatching {
        val credential = EmailAuthProvider.getCredential(email, code)
        auth.signInWithCredential(credential).await().user?.uid.orEmpty()
    }

    fun signOut() = auth.signOut()
}
