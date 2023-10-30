package com.samkt.filmio.domain.useCases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.domain.repository.GetMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingMovies @Inject constructor(
    private val getMoviesRepository: GetMoviesRepository
) {
    operator fun invoke(scope: CoroutineScope, genreId: Int? = null): Flow<PagingData<Movie>> {
        return if (genreId != null) {
            getMoviesRepository.getTrendingMovies().map {
                it.filter { movie ->
                    movie.title != null && movie.originalTitle != null && movie.genreIds.contains(genreId)
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
