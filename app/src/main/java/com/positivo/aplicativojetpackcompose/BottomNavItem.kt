package com.app.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: String
)

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home",
        icon = Icons.Filled.Home,
        screenRoute = "home"
    ),
    BottomNavItem(
        title = "Busca",
        icon = Icons.Filled.Search,
        screenRoute = "busca"
    ),
    BottomNavItem(
        title = "Perfil",
        icon = Icons.Filled.Person,
        screenRoute = "perfil"
    ),
    BottomNavItem(
        title = "Favoritos",
        icon = Icons.Filled.Favorite,
        screenRoute = "favorito"
    )
)