package com.samkt.filmio.di

import com.samkt.filmio.featureMovies.domain.repository.GetMoviesRepository
import com.samkt.filmio.featureMovies.domain.repository.GetTvSeriesRepository
import com.samkt.filmio.featureMovies.domain.useCases.GetLatestTvSeries
import com.samkt.filmio.featureMovies.domain.useCases.GetMoviesUseCase
import com.samkt.filmio.featureMovies.domain.useCases.GetPopularMovies
import com.samkt.filmio.featureMovies.domain.useCases.GetPopularTvSeries
import com.samkt.filmio.featureMovies.domain.useCases.GetTopRatedMovies
import com.samkt.filmio.featureMovies.domain.useCases.GetTrendingMovies
import com.samkt.filmio.featureMovies.domain.useCases.GetTrendingTvSeries
import com.samkt.filmio.featureMovies.domain.useCases.GetTvSeriesUseCase
import com.samkt.filmio.featureMovies.domain.useCases.GetUpcomingMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideMoviesUseCase(getMoviesRepository: GetMoviesRepository): GetMoviesUseCase {
        return GetMoviesUseCase(
            getPopularMovies = GetPopularMovies(getMoviesRepository),
            getTopRatedMovies = GetTopRatedMovies(getMoviesRepository),
            getUpcomingMovies = GetUpcomingMovies(getMoviesRepository),
            getTrendingMovies = GetTrendingMovies(getMoviesRepository)
        )
    }

    @Provides
    @Singleton
    fun provideTvSeriesUseCase(getTvSeriesRepository: GetTvSeriesRepository): GetTvSeriesUseCase {
        return GetTvSeriesUseCase(
            getPopularTvSeries = GetPopularTvSeries(getTvSeriesRepository),
            getLatestTvSeries = GetLatestTvSeries(getTvSeriesRepository),
            getTrendingTvSeries = GetTrendingTvSeries(getTvSeriesRepository)
        )
    }
}
