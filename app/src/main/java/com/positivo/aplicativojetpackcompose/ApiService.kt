package com.positivo.aplicativojetpackcompose

import com.positivo.aplicativojetpackcompose.date.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/now_playing")  // Endpoint para lançamentos
    suspend fun getLaunchMovies(@Query("api_key") apiKey: String): MovieResponse
}

