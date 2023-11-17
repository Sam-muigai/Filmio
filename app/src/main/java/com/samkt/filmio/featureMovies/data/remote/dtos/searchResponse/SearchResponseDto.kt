package com.samkt.filmio.featureMovies.data.remote.dtos.searchResponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<SearchResult>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
