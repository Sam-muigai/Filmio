package com.samkt.filmio.featureMovies.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samkt.filmio.featureMovies.data.remote.TMDBApi
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import timber.log.Timber

class PopularMoviesPagingSource(
    private val tmdbApi: TMDBApi
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = tmdbApi.getPopularMovies(page = currentPage).movies
            Timber.d("Popular movies successfully called..")
            LoadResult.Page(
                data = movies,
                nextKey = if (movies.isEmpty()) null else currentPage + 1,
                prevKey = if (currentPage == 1) null else currentPage - 1
            )
        } catch (e: Exception) {
            Timber.d(e.message)
            LoadResult.Error(e)
        }
    }
}
