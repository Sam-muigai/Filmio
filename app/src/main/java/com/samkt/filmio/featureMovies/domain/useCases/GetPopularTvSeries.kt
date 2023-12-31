package com.samkt.filmio.featureMovies.domain.useCases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.samkt.filmio.featureMovies.data.remote.dtos.TVSeries
import com.samkt.filmio.featureMovies.domain.repository.GetTvSeriesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPopularTvSeries @Inject constructor(
    private val getTvSeriesRepository: GetTvSeriesRepository
) {
    operator fun invoke(scope: CoroutineScope, genreId: Int? = null): Flow<PagingData<TVSeries>> {
        return if (genreId != null) {
            getTvSeriesRepository.getPopularTvSeries().map {
                it.filter { tvSeries ->
                    tvSeries.name != null && tvSeries.originalName != null && tvSeries.genreIds.contains(
                        genreId
                    )
                }
            }.cachedIn(scope)
        } else {
            getTvSeriesRepository.getPopularTvSeries().map {
                it.filter { tvSeries ->
                    tvSeries.name != null && tvSeries.originalName != null
                }
            }.cachedIn(scope)
        }
    }
}
