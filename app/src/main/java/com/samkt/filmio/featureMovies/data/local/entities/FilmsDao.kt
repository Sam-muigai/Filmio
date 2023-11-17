package com.samkt.filmio.featureMovies.data.local.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsDao {

    @Query("SELECT * FROM movie_entity")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM movie_entity WHERE id = :id)")
    suspend fun movieExists(id: Int): Int

    @Query("SELECT * FROM tv_series")
    fun getAllTvSeries(): Flow<List<TvSeriesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvSeries(tvSeriesEntity: TvSeriesEntity)

    @Delete
    suspend fun deleteTvSeries(tvSeriesEntity: TvSeriesEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM tv_series WHERE id = :id)")
    suspend fun tvSeriesExists(id: Int): Int
}
