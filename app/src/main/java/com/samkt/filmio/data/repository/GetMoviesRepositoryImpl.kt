package com.samkt.filmio.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samkt.filmio.data.TMDBApi
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.data.paging.PopularMoviesPagingSource
import com.samkt.filmio.data.paging.TrendingMoviesPagingSource
import com.samkt.filmio.domain.repository.GetMoviesRepository
import com.samkt.filmio.util.Constants.MOVIES_PER_PAGE
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetMoviesRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi,
) : GetMoviesRepository {
    override fun getPopularMovies(): Flow<PagingData<Result>> {
        Timber.d("getPopularMovies repository function called...")
        return Pager(
            config = PagingConfig(MOVIES_PER_PAGE),
            pagingSourceFactory = {
                PopularMoviesPagingSource(tmdbApi)
            },
        ).flow
    }

    override fun getTrendingMovies(): Flow<PagingData<Result>> {
        Timber.d("getTrending repository function called...")
        return Pager(
            config = PagingConfig(MOVIES_PER_PAGE),
            pagingSourceFactory = {
                TrendingMoviesPagingSource(tmdbApi)
            },
        ).flow
    }
}
