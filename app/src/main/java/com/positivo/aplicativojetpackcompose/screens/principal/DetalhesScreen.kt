package com.positivo.aplicativojetpackcompose.screens.principal

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.date.RetrofitInstance
import com.positivo.aplicativojetpackcompose.date.RetrofitInstance.apiService
import com.positivo.aplicativojetpackcompose.date.TvShow

@Composable
fun DetalhesScreen(
    movieId: Int,
    movieTitle: String,
    posterPath: String,
    overview: String,
    navController: NavHostController
) {
    val decodedPosterPath = Uri.decode(posterPath)
    val decodedOverview = Uri.decode(overview)

    // Obtenha o HomeViewModel
    val viewModel: HomeViewModel = viewModel()

    // Acesse os estados do ViewModel
    val popularTvShows = viewModel.popularTvShows.value
    val onAirTvShows = viewModel.onAirTvShows.value
    val isLoading = viewModel.isLoading.value

    // Carregue os dados das séries
    LaunchedEffect(Unit) {
        viewModel.getPopularTvShows(apiKey = "befc024706c98870f5f437064ebb0a18")
        viewModel.getOnAirTvShows(apiKey = "befc024706c98870f5f437064ebb0a18")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Exibe título do filme
        Text(
            text = movieTitle,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Exibe imagem do filme
        Image(
            painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500$decodedPosterPath"),
            contentDescription = movieTitle,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Descrição
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

    }}