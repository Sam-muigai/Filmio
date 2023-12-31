package com.samkt.filmio.featureMovies.presentation.singleTvSeriesScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.filmio.featureMovies.data.mappers.toTvSeriesEntity
import com.samkt.filmio.featureMovies.data.remote.dtos.TVSeries
import com.samkt.filmio.featureMovies.data.remote.dtos.credits.Cast
import com.samkt.filmio.featureMovies.data.remote.dtos.singleTvSeries.SingleTvSeriesResponse
import com.samkt.filmio.featureMovies.domain.repository.GetTvSeriesDetailsRepository
import com.samkt.filmio.featureMovies.domain.repository.LocalFilmsRepository
import com.samkt.filmio.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SingleTvSeriesViewModel @Inject constructor(
    private val singleTvSeriesDetailsRepository: GetTvSeriesDetailsRepository,
    private val localMoviesRepository: LocalFilmsRepository
) : ViewModel() {

    private val _tvSeriesUiState = MutableStateFlow(TvSeriesUiState())
    val tvSeriesUiState: StateFlow<TvSeriesUiState>
        get() = _tvSeriesUiState

    var tvSeriesSaved by mutableStateOf(0)
        private set

    fun saveTvSeries(tvSeries: SingleTvSeriesResponse) {
        viewModelScope.launch {
            localMoviesRepository.saveTvSeries(tvSeries.toTvSeriesEntity())
        }.invokeOnCompletion {
            checkIfTvSeriesSaved(tvSeries.id!!)
        }
    }

    fun deleteTvSeries(tvSeries: SingleTvSeriesResponse) {
        viewModelScope.launch {
            localMoviesRepository.deleteTvSeries(tvSeries.toTvSeriesEntity())
        }.invokeOnCompletion {
            checkIfTvSeriesSaved(tvSeries.id!!)
        }
    }

    private fun checkIfTvSeriesSaved(tvSeriesId: Int) {
        viewModelScope.launch {
            tvSeriesSaved = localMoviesRepository.tvSeriesExists(tvSeriesId)
        }
    }

    fun getTvSeries(tvSeriesId: Int) {
        checkIfTvSeriesSaved(tvSeriesId)
        viewModelScope.launch {
            _tvSeriesUiState.update {
                it.copy(
                    overviewLoading = true,
                    castLoading = true,
                    relatedLoading = true
                )
            }
            val overview = async {
                viewModelScope.launch {
                    singleTvSeriesDetailsRepository.getTvSeriesDetailsRepository(tvSeriesId)
                        .collectLatest { result ->
                            when (result) {
                                is Result.Error -> {
                                    _tvSeriesUiState.update {
                                        it.copy(
                                            overviewLoading = false,
                                            overviewError = result.message
                                        )
                                    }
                                }

                                is Result.Success -> {
                                    _tvSeriesUiState.update {
                                        it.copy(
                                            overviewLoading = false,
                                            tvSeries = result.data,
                                            overviewError = null
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            val casts = async {
                singleTvSeriesDetailsRepository.getTvSeriesCredits(tvSeriesId)
                    .collectLatest { result ->
                        when (result) {
                            is Result.Error -> {
                                _tvSeriesUiState.update {
                                    it.copy(
                                        castLoading = false,
                                        castError = result.message
                                    )
                                }
                            }

                            is Result.Success -> {
                                _tvSeriesUiState.update {
                                    it.copy(
                                        castLoading = false,
                                        cast = result.data?.cast ?: emptyList(),
                                        castError = null
                                    )
                                }
                            }
                        }
                    }
            }
            val related = async {
                singleTvSeriesDetailsRepository.getRelatedTvSeries(tvSeriesId)
                    .collectLatest { result ->
                        when (result) {
                            is Result.Error -> {
                                _tvSeriesUiState.update {
                                    it.copy(
                                        relatedLoading = false,
                                        relatedError = result.message
                                    )
                                }
                            }

                            is Result.Success -> {
                                _tvSeriesUiState.update {
                                    it.copy(
                                        relatedLoading = false,
                                        relatedTvSeries = result.data?.results ?: emptyList(),
                                        relatedError = null
                                    )
                                }
                            }
                        }
                    }
            }
            casts.await()
            overview.await()
            related.await()
        }
    }
}

data class TvSeriesUiState(
    val overviewLoading: Boolean = false,
    val tvSeries: SingleTvSeriesResponse? = null,
    val overviewError: String? = null,
    val castLoading: Boolean = false,
    val cast: List<Cast> = emptyList(),
    val castError: String? = null,
    val relatedLoading: Boolean = false,
    val relatedTvSeries: List<TVSeries> = emptyList(),
    val relatedError: String? = null
)
