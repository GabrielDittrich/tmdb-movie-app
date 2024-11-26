package com.positivo.aplicativojetpackcompose.screens.principal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.positivo.aplicativojetpackcompose.R

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val movies = homeViewModel.movies.value

    // Faça a requisição para buscar os filmes populares
    LaunchedEffect(Unit) {
        val apiKey = "befc024706c98870f5f437064ebb0a18"
        homeViewModel.getPopularMovies(apiKey)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Seção de Destaque
        item { HeaderSection() }

        // Seção de Filmes Populares
        item {
            CategorySection(
                categoryName = "Filmes Populares",
                items = movies.map { "https://image.tmdb.org/t/p/w500${it.poster_path}" },
                onItemClick = { movieUrl -> navController.navigate("detalhes/$movieUrl") }
            )
        }

        // Você pode adicionar mais categorias aqui, por exemplo:
        // item {
        //     CategorySection(
        //         categoryName = "Lançamentos",
        //         items = launchMovies, // Dados fictícios
        //         onItemClick = { movieUrl -> navController.navigate("detalhes/$movieUrl") }
        //     )
        // }
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)  // Ajuste a altura do banner
    ) {
        Image(
            painter = painterResource(R.drawable.catalogo), // Imagem de catálogo
            contentDescription = "Banner principal",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = 200f
                    )
                )
        )
        Text(
            text = "Destaques do Dia",
            color = Color.White,
            fontSize = 28.sp,  // Tamanho da fonte ajustado
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)  // Mais espaçamento
        )
    }
}

@Composable
fun CategorySection(
    categoryName: String,
    items: List<String>,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = categoryName,
            fontSize = 22.sp,  // Tamanho do título
            fontWeight = FontWeight.Bold,
            color = Color.Black,  // Cor mais nítida para o título
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                MovieCard(imageUrl = item, onClick = { onItemClick(item) })
            }
        }
    }
}

@Composable
fun MovieCard(imageUrl: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),  // Aumentar o arredondamento dos cantos
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clickable { onClick() }
            .padding(4.dp)  // Espaçamento interno dos cartões
    ) {
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = "Filme",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
