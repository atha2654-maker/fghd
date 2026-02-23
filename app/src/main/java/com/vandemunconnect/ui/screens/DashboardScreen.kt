package com.vandemunconnect.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vandemunconnect.data.model.UserProfile
import com.vandemunconnect.ui.navigation.BottomNavItem
import com.vandemunconnect.ui.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
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
                1 -> ChatTab(viewModel)
                2 -> EventsTab(viewModel)
                3 -> PlannerTab(viewModel)
                else -> ProfileTab(viewModel)
            }
        }
    }
}

@Composable
private fun HomeTab() {
    Text("Vande MUN Connect", fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    Text("Public, individual, and group conference scaffolds are enabled.")
    Text("Use Chat, Events, Planner, and Profile tabs to test app flows directly.")
}

@Composable
private fun ChatTab(viewModel: DashboardViewModel) {
    val messages by viewModel.messages.collectAsState()
    var input by rememberSaveable { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {}) { Icon(Icons.Default.Call, contentDescription = "Voice Call") }
            IconButton(onClick = {}) { Icon(Icons.Default.VideoCall, contentDescription = "Video Call") }
        }
        Text("Public Room")
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text("${message.senderId}: ${message.text}", modifier = Modifier.padding(12.dp))
                }
            }
        }
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Type message") }
        )
        Button(onClick = { viewModel.sendMessage(input); input = "" }, modifier = Modifier.fillMaxWidth()) {
            Text("Send")
        }
    }
}

@Composable
private fun EventsTab(viewModel: DashboardViewModel) {
    val events by viewModel.events.collectAsState()
    var name by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var fee by rememberSaveable { mutableStateOf("") }
    var link by rememberSaveable { mutableStateOf("") }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { Text("Latest MUN Events", fontWeight = FontWeight.Bold) }
        item {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Event name") }, modifier = Modifier.fillMaxWidth())
        }
        item {
            OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
        }
        item {
            OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date") }, modifier = Modifier.fillMaxWidth())
        }
        item {
            OutlinedTextField(value = fee, onValueChange = { fee = it }, label = { Text("Fee") }, modifier = Modifier.fillMaxWidth())
        }
        item {
            OutlinedTextField(value = link, onValueChange = { link = it }, label = { Text("Enrollment Link") }, modifier = Modifier.fillMaxWidth())
        }
        item {
            Button(onClick = { viewModel.addEvent(name, location, date, fee, link); name=""; location=""; date=""; fee=""; link="" }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Event")
            }
        }
        items(events) { event ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(event.name, fontWeight = FontWeight.SemiBold)
                    Text("${event.location} • ${event.date} • ${event.fee}")
                    Text(if (event.isPastEvent) "Past: ${event.prize}" else "Enroll: ${event.enrollmentLink}")
                }
            }
        }
    }
}

@Composable
private fun PlannerTab(viewModel: DashboardViewModel) {
    val reminders by viewModel.reminders.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Planner + Reminders", fontWeight = FontWeight.Bold)
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Reminder title") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Note") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { viewModel.addReminder(title, note); title=""; note="" }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Reminder")
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(reminders) { reminder ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(reminder.title, fontWeight = FontWeight.SemiBold)
                        Text(reminder.note)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileTab(viewModel: DashboardViewModel) {
    val profile by viewModel.profile.collectAsState()
    var draft by remember(profile) { mutableStateOf(profile) }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { Text("Editable Profile", fontWeight = FontWeight.Bold) }
        item { ProfileField("Name", draft.name) { draft = draft.copy(name = it) } }
        item { ProfileField("Portfolio", draft.portfolio) { draft = draft.copy(portfolio = it) } }
        item { ProfileField("Committee", draft.committee) { draft = draft.copy(committee = it) } }
        item { ProfileField("Contact", draft.contact) { draft = draft.copy(contact = it) } }
        item { ProfileField("Bio", draft.bio) { draft = draft.copy(bio = it) } }
        item { ProfileField("WhatsApp", draft.whatsapp) { draft = draft.copy(whatsapp = it) } }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Admin badge")
                Switch(checked = draft.isAdmin, onCheckedChange = { draft = draft.copy(isAdmin = it) })
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Owner badge")
                Switch(checked = draft.isOwner, onCheckedChange = { draft = draft.copy(isOwner = it) })
            }
        }
        item {
            Button(onClick = { viewModel.updateProfile(draft) }, modifier = Modifier.fillMaxWidth()) {
                Text("Save Profile")
            }
        }
    }
}

@Composable
private fun ProfileField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}
