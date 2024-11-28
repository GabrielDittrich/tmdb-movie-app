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

@Composable
fun BuscaScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Lógica para buscar filmes quando o query muda
    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            isLoading = true
            errorMessage = ""
            try {
                val response = apiService.searchMovies(
                    query = query,
                    apiKey = "befc024706c98870f5f437064ebb0a18",
                    language = "pt-BR"
                )
                movies = response.results
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar filmes: ${e.message}"
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
            label = { Text("Buscar filmes") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { /* Reativa o LaunchedEffect ao mudar o query */ }
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
            if (movies.isNotEmpty()) {
                Column {
                    movies.forEach { movie ->
                        MovieCard(movie = movie, navController = navController)
                    }
                }
            } else {
                // Mensagem quando não há filmes encontrados
                if (query.isNotEmpty() && !isLoading) {
                    Text(
                        text = "Nenhum filme encontrado.",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("detalhes/${movie.id}")
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.poster_path}"),
            contentDescription = movie.title,
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
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.releaseDate ?: "Data não disponível",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
