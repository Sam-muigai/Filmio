package com.samkt.filmio.data.repository

import com.samkt.filmio.data.TMDBApi
import com.samkt.filmio.data.dtos.MoviesResponseDto
import com.samkt.filmio.data.dtos.credits.CreditResponseDto
import com.samkt.filmio.data.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.domain.repository.GetMovieDetailsRepository
import com.samkt.filmio.util.Result
import com.samkt.filmio.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMovieDetailsRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : GetMovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): Flow<Result<SingleMovieResponseDto>> {
        return flowOf(safeApiCall { tmdbApi.getMovieDetails(movieId = movieId) })
    }

    override suspend fun getMovieCredits(movieId: Int): Flow<Result<CreditResponseDto>> {
        return flowOf(safeApiCall { tmdbApi.getMovieCredits(movieId) })
    }

    override suspend fun getRelatedMovies(movieId: Int): Flow<Result<MoviesResponseDto>> {
        return flowOf(safeApiCall { tmdbApi.getRelatedMovies(movieId = movieId) })
    }
}
