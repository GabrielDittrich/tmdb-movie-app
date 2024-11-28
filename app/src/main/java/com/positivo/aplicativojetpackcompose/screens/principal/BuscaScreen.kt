package com.positivo.aplicativojetpackcompose.screens.principal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.date.RetrofitInstance.apiService
import com.positivo.aplicativojetpackcompose.date.TvShow

@Composable
fun BuscaScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var tvShows by remember { mutableStateOf<List<TvShow>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            isLoading = true
            errorMessage = ""
            try {
                val movieResponse = apiService.searchMovies(
                    query = query,
                    apiKey = "befc024706c98870f5f437064ebb0a18",
                    language = "pt-BR"
                )
                val tvShowResponse = apiService.searchTvShows(
                    query = query,
                    apiKey = "befc024706c98870f5f437064ebb0a18",
                    language = "pt-BR"
                )
                movies = movieResponse.results
                tvShows = tvShowResponse.results
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar dados: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo de pesquisa
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar filmes e séries") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Aqui você precisa escolher qual filme ou série exibir.
                    // Suponhamos que você deseje abrir o primeiro item da lista de filmes:
                    if (movies.isNotEmpty()) {
                        val movie = movies[0] // Pegando o primeiro filme da lista
                        navController.navigate("detalhes/${movie.id}/${movie.title}/${movie.poster_path}/${movie.overview}")
                    }
                }
            )

        )
        Spacer(modifier = Modifier.height(16.dp))

        // Exibição do carregamento
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Exibição de mensagem de erro
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Exibe os resultados da busca
            if (movies.isNotEmpty() || tvShows.isNotEmpty()) {
                Column {
                    movies.forEach { movie ->
                        MovieCard(movie = movie, navController = navController)
                    }
                    tvShows.forEach { tvShow ->
                        TvShowCard(tvShow = tvShow, navController = navController)
                    }
                }
            } else {
                // Mensagem quando não há filmes ou séries encontrados
                if (query.isNotEmpty() && !isLoading) {
                    Text(
                        text = "Nenhum resultado encontrado.",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

// Função de Card única para TvShows e Movies
@Composable
fun MediaCard(title: String, posterPath: String?, releaseDate: String?, navController: NavController, route: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(route)
            }
    ) {
        val imageUrl = posterPath?.let {
            "https://image.tmdb.org/t/p/w500$it"
        } ?: "https://www.example.com/default_image.png" // Imagem de fallback

        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = releaseDate ?: "Data não disponível",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}


// Função de Card de TV Show
@Composable
fun TvShowCardBusca(tvShow: TvShow, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("detalhesTv/${tvShow.id}") }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${tvShow.posterPath}"),
            contentDescription = tvShow.title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp)
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterVertically)) {
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


// Função de Card de Filme
@Composable
fun MovieCard(movie: Movie, navController: NavController) {
    MediaCard(
        title = movie.title,
        posterPath = movie.poster_path,
        releaseDate = movie.releaseDate,
        navController = navController,
        route = "detalhes/${movie.id}"
    )
}
