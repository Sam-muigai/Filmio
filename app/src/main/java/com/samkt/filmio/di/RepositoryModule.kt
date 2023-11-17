package com.samkt.filmio.di

import com.samkt.filmio.featureMovies.data.local.repository.LocalFilmsRepositoryImpl
import com.samkt.filmio.featureMovies.data.remote.repository.GetMovieDetailsRepositoryImpl
import com.samkt.filmio.featureMovies.data.remote.repository.GetMoviesRepositoryImpl
import com.samkt.filmio.featureMovies.data.remote.repository.GetTvSeriesDetailRepositoryImpl
import com.samkt.filmio.featureMovies.data.remote.repository.GetTvSeriesRepositoryImpl
import com.samkt.filmio.featureMovies.data.remote.repository.SearchMovieRepositoryImpl
import com.samkt.filmio.featureMovies.domain.repository.GetMovieDetailsRepository
import com.samkt.filmio.featureMovies.domain.repository.GetMoviesRepository
import com.samkt.filmio.featureMovies.domain.repository.GetTvSeriesDetailsRepository
import com.samkt.filmio.featureMovies.domain.repository.GetTvSeriesRepository
import com.samkt.filmio.featureMovies.domain.repository.LocalFilmsRepository
import com.samkt.filmio.featureMovies.domain.repository.SearchMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGetMoviesRepository(getMoviesRepositoryImpl: GetMoviesRepositoryImpl): GetMoviesRepository

    @Binds
    @Singleton
    abstract fun bindGetTvSeriesRepository(getTvSeriesRepositoryImpl: GetTvSeriesRepositoryImpl): GetTvSeriesRepository

    @Binds
    @Singleton
    abstract fun bindGetMovieDetailsRepository(
        getMovieDetailsRepositoryImpl: GetMovieDetailsRepositoryImpl
    ): GetMovieDetailsRepository

    @Binds
    @Singleton
    abstract fun bindSearchMovieRepository(searchMovieRepositoryImpl: SearchMovieRepositoryImpl): SearchMovieRepository

    @Binds
    @Singleton
    abstract fun bindSingleTvSeriesRepository(
        getTvSeriesDetailRepositoryImpl: GetTvSeriesDetailRepositoryImpl
    ): GetTvSeriesDetailsRepository

    @Binds
    @Singleton
    abstract fun bindLocalMoviesRepository(
        localFilmsRepositoryImpl: LocalFilmsRepositoryImpl
    ): LocalFilmsRepository
}
