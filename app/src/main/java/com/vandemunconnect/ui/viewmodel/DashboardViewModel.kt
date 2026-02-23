package com.vandemunconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vandemunconnect.data.model.ChatMessage
import com.vandemunconnect.data.model.EventItem
import com.vandemunconnect.data.model.PlannerReminder
import com.vandemunconnect.data.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(id = UUID.randomUUID().toString(), senderId = "system", text = "Welcome to Vande MUN Connect public room"),
            ChatMessage(id = UUID.randomUUID().toString(), senderId = "chair", text = "Session begins at 10:00 AM")
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _events = MutableStateFlow(
        listOf(
            EventItem(
                id = UUID.randomUUID().toString(),
                name = "VandeMUN Winter Summit",
                location = "New Delhi",
                date = "2026-01-18",
                fee = "₹2500",
                enrollmentLink = "https://example.com/enroll",
                isPastEvent = false
            ),
            EventItem(
                id = UUID.randomUUID().toString(),
                name = "National Diplomacy Challenge",
                location = "Pune",
                date = "2025-10-04",
                fee = "₹1800",
                enrollmentLink = "https://example.com/past",
                prize = "Best Delegate - India Council",
                isPastEvent = true
            )
        )
    )
    val events: StateFlow<List<EventItem>> = _events

    private val _reminders = MutableStateFlow(
        listOf(
            PlannerReminder(
                id = UUID.randomUUID().toString(),
                title = "Position paper submission",
                dateEpoch = System.currentTimeMillis() + 86_400_000,
                note = "Upload before midnight"
            )
        )
    )
    val reminders: StateFlow<List<PlannerReminder>> = _reminders

    private val _profile = MutableStateFlow(
        UserProfile(
            uid = "local-user",
            name = "Delegate",
            portfolio = "UNHRC",
            committee = "Human Rights Council",
            contact = "+91 9000000000",
            bio = "Policy and diplomacy enthusiast",
            whatsapp = "+91 9000000000"
        )
    )
    val profile: StateFlow<UserProfile> = _profile

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        _messages.update { current ->
            current + ChatMessage(
                id = UUID.randomUUID().toString(),
                senderId = "local-user",
                text = text.trim()
            )
        }
    }

    fun addEvent(name: String, location: String, date: String, fee: String, link: String) {
        if (name.isBlank()) return
        _events.update { current ->
            current + EventItem(
                id = UUID.randomUUID().toString(),
                name = name.trim(),
                location = location.trim(),
                date = date.trim(),
                fee = fee.trim(),
                enrollmentLink = link.trim(),
                isPastEvent = false
            )
        }
    }

    fun addReminder(title: String, note: String) {
        if (title.isBlank()) return
        _reminders.update { current ->
            current + PlannerReminder(
                id = UUID.randomUUID().toString(),
                title = title.trim(),
                note = note.trim(),
                dateEpoch = System.currentTimeMillis() + 3_600_000
            )
        }
    }

    fun updateProfile(update: UserProfile) {
        _profile.value = update
    }
}
