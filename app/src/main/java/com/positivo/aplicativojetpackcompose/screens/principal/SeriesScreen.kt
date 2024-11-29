import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.positivo.aplicativojetpackcompose.screens.principal.HomeViewModel
import android.net.Uri
import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.screens.principal.CategorySection


@Composable
fun SeriesScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    // Séries
    val popularTvShows = homeViewModel.popularTvShows.value ?: emptyList()
    val onAirTvShows = homeViewModel.onAirTvShows.value ?: emptyList()

    // Requisição para buscar as séries
    LaunchedEffect(Unit) {
        val apiKey = "befc024706c98870f5f437064ebb0a18"
        homeViewModel.getPopularTvShows(apiKey)
        homeViewModel.getOnAirTvShows(apiKey)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Seção de Séries Populares
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

        // Seção de Séries no Ar
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
