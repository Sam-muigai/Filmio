package com.samkt.filmio.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samkt.filmio.data.TMDBApi
import com.samkt.filmio.data.dtos.Result
import timber.log.Timber

class TrendingMoviesPagingSource(
    private val tmdbApi: TMDBApi,
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? = state.anchorPosition
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1
            val movies = tmdbApi.getTrendingMovies(page = currentPage).results
            Timber.d("Trending movies successfully called..")
            LoadResult.Page(
                data = movies,
                nextKey = if (movies.isEmpty()) null else currentPage + 1,
                prevKey = if (currentPage == 1) null else currentPage - 1,
            )
        } catch (e: Exception) {
            Timber.d(e.message)
            LoadResult.Error(e)
        }
    }
}