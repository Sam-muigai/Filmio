package com.samkt.filmio.domain.repository

import androidx.paging.PagingData
import com.samkt.filmio.data.dtos.Movie
import kotlinx.coroutines.flow.Flow

interface GetMoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTrendingMovies(): Flow<PagingData<Movie>>


    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies():Flow<PagingData<Movie>>
}
