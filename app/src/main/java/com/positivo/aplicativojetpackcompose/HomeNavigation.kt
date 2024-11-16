package com.app.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.positivo.aplicativojetpackcompose.screens.principal.BuscaScreen
import com.positivo.aplicativojetpackcompose.screens.principal.FavoritosScreen
import com.positivo.aplicativojetpackcompose.screens.principal.HomeScreen
import com.positivo.aplicativojetpackcompose.screens.principal.PerfilScreen

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    val currentRoute = currentRoute(navController)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, currentRoute)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = bottomNavItems[0].screenRoute, // Define a primeira tela
            modifier = Modifier.padding(innerPadding)
        ) {
            // Defina as rotas para cada tela
            composable(bottomNavItems[0].screenRoute) { HomeScreen(navController) }
            composable(bottomNavItems[1].screenRoute) { BuscaScreen(navController) }
            composable(bottomNavItems[2].screenRoute) { PerfilScreen(navController) }
            composable(bottomNavItems[3].screenRoute) { FavoritosScreen(navController) }
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
