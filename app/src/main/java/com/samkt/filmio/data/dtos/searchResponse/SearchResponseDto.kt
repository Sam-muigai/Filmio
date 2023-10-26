package com.samkt.filmio.data.dtos.searchResponse


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