package com.samkt.filmio.data.repository

import com.samkt.filmio.data.TMDBApi
import com.samkt.filmio.data.dtos.searchResponse.SearchResult
import com.samkt.filmio.domain.repository.SearchMovieRepository
import com.samkt.filmio.util.Result
import com.samkt.filmio.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : SearchMovieRepository {
    override suspend fun searchMovie(searchQuery: String): Flow<Result<List<SearchResult>>> {
        return flowOf(safeApiCall { tmdbApi.searchMovie(searchQuery = searchQuery).results })
    }
}
