package com.app.myapplication

import android.net.Uri
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.positivo.aplicativojetpackcompose.screens.principal.BuscaScreen
import com.positivo.aplicativojetpackcompose.screens.principal.DetalhesScreen
import com.positivo.aplicativojetpackcompose.screens.principal.FavoritosScreen
import com.positivo.aplicativojetpackcompose.screens.principal.HomeScreen
import com.positivo.aplicativojetpackcompose.screens.principal.PerfilScreen

@Composable
fun HomeNavigation() {
    val navController2 = rememberNavController()
    val currentRoute = currentRoute(navController2)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController2, currentRoute)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController2,
            startDestination = bottomNavItems[0].screenRoute, // Define a primeira tela
            modifier = Modifier.padding(innerPadding)
        ) {
            // Defina as rotas para cada tela, passando o viewModel para as telas
            composable(bottomNavItems[0].screenRoute) {
                HomeScreen(navController2)
            }
            composable(bottomNavItems[1].screenRoute) {
                BuscaScreen(navController2)
            }
            composable(bottomNavItems[2].screenRoute) {
                PerfilScreen(navController2)
            }
            composable(bottomNavItems[3].screenRoute) {
                FavoritosScreen(navController2)
            }
            // No NavHost
            composable(
                route = "detalhes/{id}/{title}/{poster_path}/{overview}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("title") { type = NavType.StringType },
                    navArgument("poster_path") { type = NavType.StringType },
                    navArgument("overview") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                val title = backStackEntry.arguments?.getString("title") ?: "Título não encontrado"
                val posterPath = backStackEntry.arguments?.getString("poster_path") ?: ""
                val overview = backStackEntry.arguments?.getString("overview") ?: "Descrição não disponível"
                DetalhesScreen(movieId = id, movieTitle = title, posterPath = posterPath, overview = overview)
            }
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

