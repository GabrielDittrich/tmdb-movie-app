package com.positivo.aplicativojetpackcompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.positivo.aplicativojetpackcompose.screens.inicial.TelaCadastro
import com.positivo.aplicativojetpackcompose.screens.inicial.TelaLogin
import com.positivo.aplicativojetpackcompose.screens.inicial.TelaMenu

@Composable
fun FilmeNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "menu") {
        composable("menu") {
            TelaMenu(navController)
        }
        composable("cadastro") {
            TelaCadastro(navController)
        }
        composable("login") {
            TelaLogin(navController)
        }
    }
}
