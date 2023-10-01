package com.samkt.filmio.di

import com.samkt.filmio.data.repository.GetMoviesRepositoryImpl
import com.samkt.filmio.domain.repository.GetMoviesRepository
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
}
