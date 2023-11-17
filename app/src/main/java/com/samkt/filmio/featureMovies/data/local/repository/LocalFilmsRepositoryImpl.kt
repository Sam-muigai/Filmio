package com.samkt.filmio.featureMovies.data.local.repository

import com.samkt.filmio.featureMovies.data.local.entities.FilmDatabase
import com.samkt.filmio.featureMovies.data.local.entities.MovieEntity
import com.samkt.filmio.featureMovies.data.local.entities.TvSeriesEntity
import com.samkt.filmio.featureMovies.domain.repository.LocalFilmsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalFilmsRepositoryImpl @Inject constructor(
    filmsDatabase: FilmDatabase
) : LocalFilmsRepository {

    private val filmsDao = filmsDatabase.dao()
    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return filmsDao.getAllMovies()
    }

    override suspend fun addMovie(movieEntity: MovieEntity) {
        filmsDao.addMovie(movieEntity)
    }

    override suspend fun deleteMovies(movieEntity: MovieEntity) {
        filmsDao.deleteMovie(movieEntity)
    }

    override suspend fun movieExists(movieId: Int): Int {
        return filmsDao.movieExists(movieId)
    }

    override fun getAllTvSeries(): Flow<List<TvSeriesEntity>> {
        return filmsDao.getAllTvSeries()
    }

    override suspend fun saveTvSeries(tvSeriesEntity: TvSeriesEntity) {
        filmsDao.addTvSeries(tvSeriesEntity)
    }

    override suspend fun deleteTvSeries(tvSeriesEntity: TvSeriesEntity) {
        filmsDao.deleteTvSeries(tvSeriesEntity)
    }

    override suspend fun tvSeriesExists(tvSeriesId: Int): Int {
        return filmsDao.tvSeriesExists(tvSeriesId)
    }
}
