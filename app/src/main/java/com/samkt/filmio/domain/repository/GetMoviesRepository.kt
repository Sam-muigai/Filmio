package com.samkt.filmio.domain.repository

import androidx.paging.PagingData
import com.samkt.filmio.data.dtos.Result
import kotlinx.coroutines.flow.Flow

interface GetMoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Result>>
}
