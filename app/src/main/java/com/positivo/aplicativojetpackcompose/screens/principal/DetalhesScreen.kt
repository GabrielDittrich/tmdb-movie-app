package com.positivo.aplicativojetpackcompose.screens.principal

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.date.RetrofitInstance
import com.positivo.aplicativojetpackcompose.date.RetrofitInstance.apiService

@Composable
fun DetalhesScreen(movieId: Int, movieTitle: String, posterPath: String, overview: String) {
    val decodedPosterPath = Uri.decode(posterPath)
    val decodedOverview = Uri.decode(overview)
    val viewModel: HomeViewModel = viewModel()
    val movie = remember { mutableStateOf<Movie?>(null) }

    LaunchedEffect(movieId) {
        try {
            val response = apiService.getMovieDetails(
                movieId,
                apiKey = "befc024706c98870f5f437064ebb0a18",
                language = "pt-BR"
            )
            movie.value = response
        } catch (e: Exception) {
            println("Erro ao carregar os detalhes do filme: ${e.message}")
        }
    }

    movie.value?.let { movie ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = movie.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Ajustando a imagem para o tamanho ideal na tela de detalhes
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                contentDescription = movie.title,
                contentScale = ContentScale.Fit, // Ajusta a imagem para caber bem no espaço disponível
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp) // Tamanho ajustado para a imagem no detalhe
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Descrição",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = decodedOverview,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Data de Lançamento",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = movie.releaseDate ?: "Data não disponível",
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
