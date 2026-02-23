package com.vandemunconnect.ui.navigation

sealed class Routes(val route: String) {
    data object Login : Routes("login")
    data object Verification : Routes("verification")
    data object Dashboard : Routes("dashboard")
}
