package com.samkt.filmio.featureMovies.data.remote.repository


import com.samkt.filmio.featureMovies.data.remote.TMDBApi
import com.samkt.filmio.featureMovies.data.remote.dtos.searchResponse.SearchResult
import com.samkt.filmio.featureMovies.domain.repository.SearchMovieRepository
import com.samkt.filmio.util.Result
import com.samkt.filmio.util.safeApiCall
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchMovieRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : SearchMovieRepository {
    override suspend fun searchMovie(searchQuery: String): Flow<Result<List<SearchResult>>> {
        return flowOf(safeApiCall { tmdbApi.searchMovie(searchQuery = searchQuery).results })
    }
}
