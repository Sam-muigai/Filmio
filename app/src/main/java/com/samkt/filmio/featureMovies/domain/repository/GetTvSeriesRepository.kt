package com.samkt.filmio.featureMovies.domain.repository

import androidx.paging.PagingData
import com.samkt.filmio.featureMovies.data.remote.dtos.TVSeries
import kotlinx.coroutines.flow.Flow

interface GetTvSeriesRepository {

    fun getPopularTvSeries(): Flow<PagingData<TVSeries>>
    fun getTrendingTvSeries(): Flow<PagingData<TVSeries>>

    fun getLatestTvSeries(): Flow<PagingData<TVSeries>>
}
