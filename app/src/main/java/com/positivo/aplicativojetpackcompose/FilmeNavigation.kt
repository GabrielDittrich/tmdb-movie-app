package com.positivo.aplicativojetpackcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.myapplication.HomeNavigation
import com.google.firebase.auth.FirebaseAuth
import com.positivo.aplicativojetpackcompose.screens.inicial.TelaCadastro
import com.positivo.aplicativojetpackcompose.screens.inicial.TelaLogin
import com.positivo.aplicativojetpackcompose.screens.inicial.TelaMenu

@Composable
fun FilmeNavigation(navController: NavHostController, auth: FirebaseAuth) {
    // Verifique se o usuário está logado
    val currentUser = auth.currentUser

    // Obter o ViewModelStoreOwner da Activity
    val viewModelStoreOwner = LocalContext.current as ViewModelStoreOwner

    // Certifique-se de que o ViewModelStore seja configurado antes de setar o NavHost
    navController.setViewModelStore(viewModelStoreOwner.viewModelStore)

    val startDestination = if (currentUser != null) "home_navigation" else "menu"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("menu") { TelaMenu(navController) }
        composable("cadastro") { TelaCadastro(navController) }
        composable("login") { TelaLogin(navController) }
        composable("home_navigation") { HomeNavigation() }
    }
}
