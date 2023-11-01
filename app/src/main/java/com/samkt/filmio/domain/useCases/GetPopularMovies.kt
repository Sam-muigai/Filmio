package com.samkt.filmio.domain.useCases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.samkt.filmio.data.remote.dtos.Movie
import com.samkt.filmio.domain.repository.GetMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPopularMovies @Inject constructor(
    private val getMoviesRepository: GetMoviesRepository
) {
    operator fun invoke(scope: CoroutineScope, genreIds: Int? = null): Flow<PagingData<Movie>> {
        return if (genreIds != null) {
            getMoviesRepository.getPopularMovies().map {
                it.filter { movie ->
                    movie.title != null && movie.originalTitle != null && movie.genreIds.contains(
                        genreIds
                    )
                }
            }.cachedIn(scope)
        } else {
            getMoviesRepository.getPopularMovies().map {
                it.filter { movie ->
                    movie.title != null || movie.originalTitle != null
                }
            }.cachedIn(scope)
        }
    }
}
