package com.samkt.filmio.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samkt.filmio.data.TMDBApi
import com.samkt.filmio.data.dtos.Movie
import timber.log.Timber

class TopRatedPagingSource(
    private val tmdbApi: TMDBApi
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = tmdbApi.getTopRatedMovies(page = currentPage).movies
            Timber.d("Top rated movies successfully called..")
            LoadResult.Page(
                data = movies,
                nextKey = if (movies.isEmpty()) null else currentPage + 1,
                prevKey = if (currentPage == 1) null else currentPage - 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
