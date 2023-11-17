package com.samkt.filmio.featureMovies.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samkt.filmio.featureMovies.data.remote.TMDBApi
import com.samkt.filmio.featureMovies.data.remote.dtos.TVSeries
import timber.log.Timber

class TrendingTvSeriesPagingSource(
    private val tmdbApi: TMDBApi
) : PagingSource<Int, TVSeries>() {
    override fun getRefreshKey(state: PagingState<Int, TVSeries>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVSeries> {
        return try {
            val currentPage = params.key ?: 1
            val tvSeries = tmdbApi.getTrendingTvSeries(page = currentPage).results
            Timber.d("TV series successfully called..")
            LoadResult.Page(
                data = tvSeries,
                nextKey = if (tvSeries.isEmpty()) null else currentPage + 1,
                prevKey = if (currentPage == 1) null else currentPage - 1
            )
        } catch (e: Exception) {
            Timber.d(e.message)
            LoadResult.Error(e)
        }
    }
}
