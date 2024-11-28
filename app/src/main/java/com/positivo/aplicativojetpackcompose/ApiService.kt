package com.positivo.aplicativojetpackcompose

import com.positivo.aplicativojetpackcompose.date.Movie
import com.positivo.aplicativojetpackcompose.date.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String,@Query("language") language: String = "pt-BR"): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String,@Query("language") language: String = "pt-BR"): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String,@Query("language") language: String = "pt-BR" ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getLaunchMovies(@Query("api_key",) apiKey: String): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "pt-BR", // Defina "pt-BR" como padrão, se preferir
        @Query("api_key") apiKey: String
    ): Movie


}


