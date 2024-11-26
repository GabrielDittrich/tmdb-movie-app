package com.positivo.aplicativojetpackcompose.screens.principal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.date.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    private val _launchMovies = mutableStateOf<List<Movie>>(emptyList())  // Para lançamentos
    val launchMovies: State<List<Movie>> = _launchMovies

    // Função para buscar filmes populares
    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getPopularMovies(apiKey)
                _movies.value = response.results
            } catch (e: Exception) {
                // Trate erros de rede aqui, se necessário
            }
        }
    }

    // Função para buscar lançamentos
    fun getLaunchMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getLaunchMovies(apiKey)
                _launchMovies.value = response.results
            } catch (e: Exception) {
                // Trate erros de rede aqui, se necessário
            }
        }
    }
}
