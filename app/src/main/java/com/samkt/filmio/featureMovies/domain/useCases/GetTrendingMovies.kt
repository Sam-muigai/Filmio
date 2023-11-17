package com.samkt.filmio.featureMovies.domain.useCases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import com.samkt.filmio.featureMovies.domain.repository.GetMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrendingMovies @Inject constructor(
    private val getMoviesRepository: GetMoviesRepository
) {
    operator fun invoke(scope: CoroutineScope, genreId: Int? = null): Flow<PagingData<Movie>> {
        return if (genreId != null) {
            getMoviesRepository.getTrendingMovies().map {
                it.filter { movie ->
                    movie.title != null && movie.originalTitle != null && movie.genreIds.contains(
                        genreId
                    )
                }
            }.cachedIn(scope)
        } else {
            getMoviesRepository.getTrendingMovies().map {
                it.filter { movie ->
                    movie.title != null || movie.originalTitle != null
                }
            }.cachedIn(scope)
        }
    }
}
