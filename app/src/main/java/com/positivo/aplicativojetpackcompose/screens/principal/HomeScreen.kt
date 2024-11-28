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
import androidx.compose.runtime.getValue
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.positivo.aplicativojetpackcompose.R
import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.date.TvShow

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val movies = homeViewModel.movies.value
    val launchMovies = homeViewModel.launchMovies.value
    val topRatedMovies = homeViewModel.topRatedMovies.value
    val nowPlayingMovies = homeViewModel.nowPlayingMovies.value
    // Séries
    val popularTvShows = homeViewModel.popularTvShows.value ?: emptyList()
    val onAirTvShows = homeViewModel.onAirTvShows.value ?: emptyList()

    // Requisição para buscar os filmes
    LaunchedEffect(Unit) {
        val apiKey = "befc024706c98870f5f437064ebb0a18"
        homeViewModel.getPopularMovies(apiKey)
        homeViewModel.getLaunchMovies(apiKey)
        homeViewModel.getTopRatedMovies(apiKey)
        homeViewModel.getNowPlayingMovies(apiKey)

        homeViewModel.getPopularTvShows(apiKey)
        homeViewModel.getOnAirTvShows(apiKey)
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
                    val encodedOverview = Uri.encode(movie.overview ?: "") // Certifique-se de codificar a descrição
                    navController.navigate("detalhes/${movie.id}/$encodedTitle/$encodedPosterPath/$encodedOverview")
                }
            )
        }

        // Categorias de Séries
        item {
            CategorySection(
                categoryName = "Séries Populares",
                items = popularTvShows.map {
                    Movie(it.id, it.title, it.posterPath.toString(), it.overview, it.firstAirDate ?: "")
                },
                onItemClick = { tvShow ->
                    val encodedPosterPath = Uri.encode(tvShow.poster_path)
                    val encodedOverview = Uri.encode(tvShow.overview ?: "")
                    navController.navigate("detalhes/${tvShow.id}/${Uri.encode(tvShow.title)}/$encodedPosterPath/$encodedOverview")
                }
            )
        }

        item {
            CategorySection(
                categoryName = "Séries no Ar",
                items = onAirTvShows.map {
                    Movie(it.id, it.title, it.posterPath.toString(), it.overview, it.firstAirDate ?: "")
                },
                onItemClick = { tvShow ->
                    val encodedPosterPath = Uri.encode(tvShow.poster_path)
                    val encodedOverview = Uri.encode(tvShow.overview ?: "")
                    navController.navigate("detalhes/${tvShow.id}/${Uri.encode(tvShow.title)}/$encodedPosterPath/$encodedOverview")
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
@Composable
fun ContentScreen(
    navController: NavController,
    title: String,
    contentItems: List<Movie>, // Pode ser adaptado para uma classe base comum se necessário
    onItemClick: (Movie) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título da seção
        item {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Lista horizontal dos itens
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(contentItems) { item ->
                    MovieCard(
                        imageUrl = "https://image.tmdb.org/t/p/w500${item.poster_path}",
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun UnifiedScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val movies = viewModel.movies.value
    val tvShows = viewModel.popularTvShows.value

    LaunchedEffect(Unit) {
        val apiKey = "sua_api_key"
        viewModel.getPopularMovies(apiKey)
        viewModel.getPopularTvShows(apiKey)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Seção de filmes
        item {
            ContentScreen(
                navController = navController,
                title = "Filmes Populares",
                contentItems = movies,
                onItemClick = { movie ->
                    val encodedPosterPath = Uri.encode(movie.poster_path)
                    val encodedOverview = Uri.encode(movie.overview ?: "")
                    navController.navigate("detalhes/${movie.id}/${Uri.encode(movie.title)}/$encodedPosterPath/$encodedOverview")
                }
            )
        }

        // Seção de séries
        item {
            ContentScreen(
                navController = navController,
                title = "Séries Populares",
                contentItems = tvShows.map {
                    Movie(it.id, it.title, it.posterPath.toString(), it.overview, it.firstAirDate ?: "")
                },
                onItemClick = { tvShow ->
                    val encodedPosterPath = Uri.encode(tvShow.poster_path)
                    val encodedOverview = Uri.encode(tvShow.overview ?: "")
                    navController.navigate("detalhes/${tvShow.id}/${Uri.encode(tvShow.title)}/$encodedPosterPath/$encodedOverview")
                }
            )
        }
    }
}

@Composable
fun TvShowsScreen(viewModel: HomeViewModel, navController: NavController) {
    val popularTvShows by viewModel.popularTvShows
    val onAirTvShows by viewModel.onAirTvShows

    LaunchedEffect(Unit) {
        viewModel.getPopularTvShows(apiKey = "sua_api_key")
        viewModel.getOnAirTvShows(apiKey = "sua_api_key")
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Séries Populares", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        LazyColumn {
            items(popularTvShows) { tvShow ->
                TvShowCard(tvShow = tvShow, navController = navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Séries no Ar", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        LazyColumn {
            items(onAirTvShows) { tvShow ->
                TvShowCard(tvShow = tvShow, navController = navController)
            }
        }
    }
}

@Composable
fun TvShowCard(tvShow: TvShow, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Navegar para detalhes da série */ }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${tvShow.posterPath}"),
            contentDescription = tvShow.title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp)
        )
        Column(modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)) {
            Text(text = tvShow.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tvShow.firstAirDate ?: "Data não disponível",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
