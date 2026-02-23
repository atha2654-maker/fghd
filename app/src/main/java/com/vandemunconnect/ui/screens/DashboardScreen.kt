package com.vandemunconnect.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vandemunconnect.ui.navigation.BottomNavItem

@Composable
fun DashboardScreen() {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Chat, BottomNavItem.Events, BottomNavItem.Planner, BottomNavItem.Profile)
    var selected by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            when (selected) {
                0 -> HomeTab()
                1 -> ChatTab()
                2 -> EventsTab()
                3 -> PlannerTab()
                else -> ProfileTab()
            }
        }
    }
}

@Composable
private fun HomeTab() {
    Text("Welcome to your MUN dashboard")
    Text("Features: public/individual/group conference, whiteboard, and screen sharing ready.")
}

@Composable
private fun ChatTab() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {}) { Icon(Icons.Default.Call, contentDescription = "Voice Call") }
            IconButton(onClick = {}) { Icon(Icons.Default.VideoCall, contentDescription = "Video Call") }
        }
        Text("1:1 chat, voice message input, public room, and voice chat channels.")
        OutlinedButton(onClick = {}) { Text("Open Public Chat Room") }
        OutlinedButton(onClick = {}) { Text("Record Voice Message") }
    }
}

@Composable
private fun EventsTab() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Latest MUN Events")
        Text("• Event name, location, date, fee, enrollment link + button")
        OutlinedButton(onClick = {}) { Text("Enroll Now") }
        Text("Past events with prizes and achievements")
    }
}

@Composable
private fun PlannerTab() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Calendar View + Date reminders + Notifications")
        OutlinedButton(onClick = {}) { Text("Add Reminder") }
    }
}

@Composable
private fun ProfileTab() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Editable Profile")
        Text("Name, portfolio, committee, contact, bio, WhatsApp")
        Text("Admin badge / Owner badge support")
    }
}
