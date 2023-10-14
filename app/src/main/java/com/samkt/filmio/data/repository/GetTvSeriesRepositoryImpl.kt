package com.samkt.filmio.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samkt.filmio.data.TMDBApi
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.data.paging.LatestTvSeriesPagingSource
import com.samkt.filmio.data.paging.PopularTvSeriesPagingSource
import com.samkt.filmio.data.paging.TrendingTvSeriesPagingSource
import com.samkt.filmio.domain.repository.GetTvSeriesRepository
import com.samkt.filmio.util.Constants
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetTvSeriesRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
):GetTvSeriesRepository {

    override fun getPopularTvSeries(): Flow<PagingData<TVSeries>> {
        Timber.d("getPopularTvSeries repository function called...")
        return Pager(
            config = PagingConfig(Constants.MOVIES_PER_PAGE),
            pagingSourceFactory = {
                PopularTvSeriesPagingSource(tmdbApi)
            },
        ).flow
    }

    override fun getTrendingTvSeries(): Flow<PagingData<TVSeries>> {
        Timber.d("getPopularTvSeries repository function called...")
        return Pager(
            config = PagingConfig(Constants.MOVIES_PER_PAGE),
            pagingSourceFactory = {
                TrendingTvSeriesPagingSource(tmdbApi)
            },
        ).flow
    }

    override fun getLatestTvSeries(): Flow<PagingData<TVSeries>> {
        Timber.d("getLatestTvSeries repository function called...")
        return Pager(
            config = PagingConfig(Constants.MOVIES_PER_PAGE),
            pagingSourceFactory = {
                LatestTvSeriesPagingSource(tmdbApi)
            },
        ).flow
    }

}