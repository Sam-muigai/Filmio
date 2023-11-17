package com.samkt.filmio.featureMovies.domain.repository

import androidx.paging.PagingData
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import kotlinx.coroutines.flow.Flow

interface GetMoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTrendingMovies(): Flow<PagingData<Movie>>

    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>
}
