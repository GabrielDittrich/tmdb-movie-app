package com.positivo.aplicativojetpackcompose.screens.principal

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import coil.compose.rememberImagePainter
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
            // Verifique o conteúdo da resposta no log
            println("Resposta da API: ${response.title}")
            movie.value = response
        } catch (e: Exception) {
            // Lidar com erros
            println("Erro ao carregar os detalhes do filme: ${e.message}")
        }
    }

    movie.value?.let { movie ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
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
                lineHeight = 24.sp, // Facilita a leitura
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