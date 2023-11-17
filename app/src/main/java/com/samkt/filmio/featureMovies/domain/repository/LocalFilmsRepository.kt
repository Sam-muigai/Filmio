package com.samkt.filmio.featureMovies.domain.repository

import com.samkt.filmio.featureMovies.data.local.entities.MovieEntity
import com.samkt.filmio.featureMovies.data.local.entities.TvSeriesEntity
import kotlinx.coroutines.flow.Flow

interface LocalFilmsRepository {
    fun getAllMovies(): Flow<List<MovieEntity>>
    suspend fun addMovie(movieEntity: MovieEntity)
    suspend fun deleteMovies(movieEntity: MovieEntity)
    suspend fun movieExists(movieId: Int): Int
    fun getAllTvSeries(): Flow<List<TvSeriesEntity>>
    suspend fun saveTvSeries(tvSeriesEntity: TvSeriesEntity)
    suspend fun deleteTvSeries(tvSeriesEntity: TvSeriesEntity)
    suspend fun tvSeriesExists(tvSeriesId: Int): Int
}
