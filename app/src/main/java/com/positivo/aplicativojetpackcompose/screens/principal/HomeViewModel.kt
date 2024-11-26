package com.positivo.aplicativojetpackcompose.screens.principal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.positivo.aplicativojetpackcompose.ApiService
import com.positivo.aplicativojetpackcompose.date.Movie
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val apiService = ApiService.create()
    private val _movies = mutableStateOf<List<Movie>>(emptyList()) // Estado mutável
    val movies: State<List<Movie>> get() = _movies // Acessar o estado imutável

    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPopularMovies(apiKey)
                _movies.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
