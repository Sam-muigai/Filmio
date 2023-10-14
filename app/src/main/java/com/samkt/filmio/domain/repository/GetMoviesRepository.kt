package com.samkt.filmio.domain.repository

import androidx.paging.PagingData
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.data.dtos.TVSeries
import kotlinx.coroutines.flow.Flow

interface GetMoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Result>>

    fun getTrendingMovies(): Flow<PagingData<Result>>


    fun getUpcomingMovies(): Flow<PagingData<Result>>

    fun getTopRatedMovies():Flow<PagingData<Result>>
}
