package com.positivo.aplicativojetpackcompose.date

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String // Certifique-se disso
)
