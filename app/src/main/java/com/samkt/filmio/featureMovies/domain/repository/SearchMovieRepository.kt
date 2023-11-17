package com.samkt.filmio.featureMovies.domain.repository

import com.samkt.filmio.featureMovies.data.remote.dtos.searchResponse.SearchResult
import com.samkt.filmio.util.Result
import kotlinx.coroutines.flow.Flow

interface SearchMovieRepository {

    suspend fun searchMovie(searchQuery: String): Flow<Result<List<SearchResult>>>
}
