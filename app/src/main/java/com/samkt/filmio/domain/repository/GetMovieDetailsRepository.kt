package com.samkt.filmio.domain.repository

import com.samkt.filmio.data.remote.dtos.MoviesResponseDto
import com.samkt.filmio.data.remote.dtos.credits.CreditResponseDto
import com.samkt.filmio.data.remote.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.util.Result
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): Flow<Result<SingleMovieResponseDto>>

    suspend fun getMovieCredits(movieId: Int): Flow<Result<CreditResponseDto>>

    suspend fun getRelatedMovies(movieId: Int): Flow<Result<MoviesResponseDto>>
}
