package com.samkt.filmio.di

import com.samkt.filmio.data.repository.GetMovieDetailsRepositoryImpl
import com.samkt.filmio.data.repository.GetMoviesRepositoryImpl
import com.samkt.filmio.data.repository.GetTvSeriesDetailRepositoryImpl
import com.samkt.filmio.data.repository.GetTvSeriesRepositoryImpl
import com.samkt.filmio.data.repository.SearchMovieRepositoryImpl
import com.samkt.filmio.domain.repository.GetMovieDetailsRepository
import com.samkt.filmio.domain.repository.GetMoviesRepository
import com.samkt.filmio.domain.repository.GetTvSeriesDetailsRepository
import com.samkt.filmio.domain.repository.GetTvSeriesRepository
import com.samkt.filmio.domain.repository.SearchMovieRepository
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
    abstract fun bindGetMovieDetailsRepository(getMovieDetailsRepositoryImpl: GetMovieDetailsRepositoryImpl): GetMovieDetailsRepository

    @Binds
    @Singleton
    abstract fun bindSearchMovieRepository(searchMovieRepositoryImpl: SearchMovieRepositoryImpl): SearchMovieRepository

    @Binds
    @Singleton
    abstract fun bindSingleTvSeriesRepository(getTvSeriesDetailRepositoryImpl: GetTvSeriesDetailRepositoryImpl):GetTvSeriesDetailsRepository
}
