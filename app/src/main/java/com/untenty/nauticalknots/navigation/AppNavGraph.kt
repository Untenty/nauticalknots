package com.untenty.nauticalknots.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    knotsListScreenContent: @Composable () -> Unit,
    knotCardScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController, startDestination = Screen.KnotsList.route
    ) {
        composable(Screen.KnotsList.route) { knotsListScreenContent() }
        composable(Screen.KnotCard.route) { knotCardScreenContent() }
        composable(Screen.Settings.route) { settingsScreenContent() }
    }
}