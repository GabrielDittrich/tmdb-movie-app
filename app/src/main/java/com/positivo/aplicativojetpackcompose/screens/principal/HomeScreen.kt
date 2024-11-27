package com.positivo.aplicativojetpackcompose.screens.principal

import android.net.Uri
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
import com.positivo.aplicativojetpackcompose.date.Movie

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val movies = homeViewModel.movies.value
    val launchMovies = homeViewModel.launchMovies.value
    val topRatedMovies = homeViewModel.topRatedMovies.value
    val nowPlayingMovies = homeViewModel.nowPlayingMovies.value

    // Requisição para buscar os filmes
    LaunchedEffect(Unit) {
        val apiKey = "befc024706c98870f5f437064ebb0a18"
        homeViewModel.getPopularMovies(apiKey)
        homeViewModel.getLaunchMovies(apiKey)
        homeViewModel.getTopRatedMovies(apiKey)
        homeViewModel.getNowPlayingMovies(apiKey)
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
                items = movies,
                onItemClick = { movie ->
                    val encodedPosterPath = Uri.encode(movie.poster_path)
                    val encodedOverview = Uri.encode(movie.overview ?: "")  // Codificar a descrição
                    navController.navigate("detalhes/${movie.id}/${Uri.encode(movie.title)}/$encodedPosterPath/$encodedOverview")
                }
            )
        }

        // Seção de Filmes de Lançamento
        item {
            CategorySection(
                categoryName = "Lançamentos",
                items = launchMovies,
                onItemClick = { movie ->
                    val encodedPosterPath = Uri.encode(movie.poster_path)
                    val encodedOverview = Uri.encode(movie.overview ?: "")
                    navController.navigate("detalhes/${movie.id}/${Uri.encode(movie.title)}/$encodedPosterPath/$encodedOverview")
                }
            )
        }

        // Seção de Filmes Mais Bem Avaliados
        item {
            CategorySection(
                categoryName = "Melhores Avaliados",
                items = topRatedMovies,
                onItemClick = { movie ->
                    val encodedPosterPath = Uri.encode(movie.poster_path)
                    val encodedTitle = Uri.encode(movie.title)
                    navController.navigate("detalhes/${movie.id}/$encodedTitle/$encodedPosterPath")
                }
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)  // Ajuste a altura do banner
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
    items: List<Movie>,  // Modificado para usar a lista de objetos Movie
    onItemClick: (Movie) -> Unit
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
                MovieCard(imageUrl = "https://image.tmdb.org/t/p/w500${item.poster_path}", onClick = { onItemClick(item) })
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
