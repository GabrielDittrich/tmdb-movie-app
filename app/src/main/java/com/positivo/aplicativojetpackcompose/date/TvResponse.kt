package com.positivo.aplicativojetpackcompose.date

import com.google.gson.annotations.SerializedName

data class TvResponse(
    val results: List<TvShow>
)

data class TvShow(
    val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("first_air_date") val firstAirDate: String?
)
