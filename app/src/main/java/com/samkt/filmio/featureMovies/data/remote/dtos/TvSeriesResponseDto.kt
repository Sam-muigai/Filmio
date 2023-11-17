package com.samkt.filmio.featureMovies.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeriesResponseDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<TVSeries>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
