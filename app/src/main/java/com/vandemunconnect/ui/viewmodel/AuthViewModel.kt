package com.vandemunconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vandemunconnect.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isAuthenticated = MutableStateFlow(authRepository.currentUserId() != null)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    fun completeVerification() {
        _isAuthenticated.value = true
    }
}
