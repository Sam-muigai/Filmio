package com.samkt.filmio.domain.repository

import com.samkt.filmio.data.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.util.Result
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsRepository {
    suspend fun getMovieDetails(movieId:Int): Flow<Result<SingleMovieResponseDto>>

}
