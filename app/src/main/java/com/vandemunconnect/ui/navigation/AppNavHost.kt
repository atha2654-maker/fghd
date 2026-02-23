package com.vandemunconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vandemunconnect.ui.screens.DashboardScreen
import com.vandemunconnect.ui.screens.LoginScreen
import com.vandemunconnect.ui.screens.VerificationScreen
import com.vandemunconnect.ui.viewmodel.AuthViewModel

@Composable
fun AppNavHost(authViewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isAuthenticated = authViewModel.isAuthenticated.collectAsState().value
    val start = if (isAuthenticated) Routes.Dashboard.route else Routes.Login.route

    NavHost(navController = navController, startDestination = start) {
        composable(Routes.Login.route) {
            LoginScreen(onNext = { navController.navigate(Routes.Verification.route) })
        }
        composable(Routes.Verification.route) {
            VerificationScreen(onVerified = {
                authViewModel.completeVerification()
                navController.navigate(Routes.Dashboard.route) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            })
        }
        composable(Routes.Dashboard.route) {
            DashboardScreen()
        }
    }
}
