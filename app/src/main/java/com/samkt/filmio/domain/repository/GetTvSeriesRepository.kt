package com.samkt.filmio.domain.repository

import androidx.paging.PagingData
import com.samkt.filmio.data.dtos.TVSeries
import kotlinx.coroutines.flow.Flow

interface GetTvSeriesRepository {

    fun getPopularTvSeries(): Flow<PagingData<TVSeries>>
    fun getTrendingTvSeries(): Flow<PagingData<TVSeries>>

    fun getLatestTvSeries():Flow<PagingData<TVSeries>>
}


