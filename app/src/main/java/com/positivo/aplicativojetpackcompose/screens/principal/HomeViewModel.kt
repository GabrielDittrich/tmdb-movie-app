package com.positivo.aplicativojetpackcompose.screens.principal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.date.RetrofitInstance
import com.positivo.aplicativojetpackcompose.date.TvShow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _movies = mutableStateOf<List<Movie>>(emptyList())  // Filmes populares
    val movies: State<List<Movie>> = _movies

    private val _launchMovies = mutableStateOf<List<Movie>>(emptyList())  // Filmes de lançamentos
    val launchMovies: State<List<Movie>> = _launchMovies

    private val _topRatedMovies = mutableStateOf<List<Movie>>(emptyList())  // Filmes mais bem avaliados
    val topRatedMovies: State<List<Movie>> = _topRatedMovies

    private val _nowPlayingMovies = mutableStateOf<List<Movie>>(emptyList())  // Filmes em exibição
    val nowPlayingMovies: State<List<Movie>> = _nowPlayingMovies

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _popularTvShows = mutableStateOf<List<TvShow>>(emptyList())
    val popularTvShows: State<List<TvShow>> = _popularTvShows

    private val _onAirTvShows = mutableStateOf<List<TvShow>>(emptyList())
    val onAirTvShows: State<List<TvShow>> = _onAirTvShows

    fun getPopularTvShows(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getPopularTvShows(apiKey)
                _popularTvShows.value = response.results
            } catch (e: Exception) {
                // Trate erros aqui
            }
        }
    }

    fun getOnAirTvShows(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getOnAirTvShows(apiKey)
                _onAirTvShows.value = response.results
            } catch (e: Exception) {
                // Trate erros aqui
            }
        }
    }

    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.apiService.getPopularMovies(apiKey)
                _movies.value = response.results
            } catch (e: Exception) {
                // Trate o erro
            } finally {
                _isLoading.value = false
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

    // Função para buscar filmes mais bem avaliados
    fun getTopRatedMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getTopRatedMovies(apiKey)
                _topRatedMovies.value = response.results
            } catch (e: Exception) {
                // Trate erros de rede aqui, se necessário
            }
        }
    }

    // Função para buscar filmes em exibição
    fun getNowPlayingMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getNowPlayingMovies(apiKey)
                _nowPlayingMovies.value = response.results
            } catch (e: Exception) {
                // Trate erros de rede aqui, se necessário
            }
        }
    }
}
