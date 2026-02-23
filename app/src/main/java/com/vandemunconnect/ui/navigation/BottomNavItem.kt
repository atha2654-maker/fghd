package com.vandemunconnect.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    data object Home : BottomNavItem("Home", Icons.Default.Home)
    data object Chat : BottomNavItem("Chat", Icons.Default.Chat)
    data object Events : BottomNavItem("Events", Icons.Default.Event)
    data object Planner : BottomNavItem("Planner", Icons.Default.Today)
    data object Profile : BottomNavItem("Profile", Icons.Default.AccountCircle)
}
