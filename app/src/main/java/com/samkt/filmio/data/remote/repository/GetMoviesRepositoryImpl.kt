package com.samkt.filmio.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samkt.filmio.data.remote.TMDBApi
import com.samkt.filmio.data.remote.dtos.Movie
import com.samkt.filmio.data.remote.paging.PopularMoviesPagingSource
import com.samkt.filmio.data.remote.paging.TopRatedPagingSource
import com.samkt.filmio.data.remote.paging.TrendingMoviesPagingSource
import com.samkt.filmio.data.remote.paging.UpcomingMoviesPagingSource
import com.samkt.filmio.domain.repository.GetMoviesRepository
import com.samkt.filmio.util.Constants.MOVIES_PER_PAGE
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class GetMoviesRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : GetMoviesRepository {
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        Timber.d("getPopularMovies repository function called...")
        return Pager(
            config = PagingConfig(MOVIES_PER_PAGE),
            pagingSourceFactory = {
                PopularMoviesPagingSource(tmdbApi)
            }
        ).flow
    }

    override fun getTrendingMovies(): Flow<PagingData<Movie>> {
        Timber.d("getTrending repository function called...")
        return Pager(
            config = PagingConfig(MOVIES_PER_PAGE),
            pagingSourceFactory = {
                TrendingMoviesPagingSource(tmdbApi)
            }
        ).flow
    }

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        Timber.d("getUpcomingMovies repository function called...")
        return Pager(
            config = PagingConfig(MOVIES_PER_PAGE),
            pagingSourceFactory = {
                UpcomingMoviesPagingSource(tmdbApi)
            }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        Timber.d("getTopRated repository function called...")
        return Pager(
            config = PagingConfig(MOVIES_PER_PAGE),
            pagingSourceFactory = {
                TopRatedPagingSource(tmdbApi)
            }
        ).flow
    }
}
