package com.samkt.filmio.presentation.singleMovieScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.filmio.data.dtos.credits.Cast
import com.samkt.filmio.data.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.domain.repository.GetMovieDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.util.Result
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    private val movieDetailsRepository: GetMovieDetailsRepository
) : ViewModel() {

    private val _movieScreenUiState = MutableStateFlow(MovieScreenUiState())
    val movieScreenUiState: StateFlow<MovieScreenUiState>
        get() = _movieScreenUiState

    fun getMovieDetails(movieId: Int) {
        _movieScreenUiState.update {
            it.copy(
                loading = true
            )
        }
        Timber.d("GetMovieDetails function called")
        viewModelScope.launch {
            val related = async {
                movieDetailsRepository.getRelatedMovies(movieId).collectLatest { result ->
                    when (result) {
                        is Result.Error -> {
                            Timber.d("GetMovieDetails: Error occurred")
                            _movieScreenUiState.update { state ->
                                state.copy(
                                    loading = false,
                                    relatedMoviesError = result.message
                                )
                            }
                        }

                        is Result.Success -> {
                            Timber.d("GetMovieDetails : Successfully retrieved details")
                            val movies = result.data?.movies
                            _movieScreenUiState.update { state ->
                                state.copy(
                                    loading = false,
                                    overViewError = null,
                                    relatedMovies = movies ?: emptyList()
                                )
                            }
                        }
                    }
                }
            }

            val credit = async {
                movieDetailsRepository.getMovieCredits(movieId = movieId).collectLatest { result ->
                    when (result) {
                        is Result.Error -> {
                            _movieScreenUiState.update { state ->
                                state.copy(
                                    loading = false,
                                    castError = result.message
                                )
                            }
                        }

                        is Result.Success -> {
                            _movieScreenUiState.update { state ->
                                state.copy(
                                    loading = false,
                                    castError = null,
                                    cast = result.data?.cast ?: emptyList()
                                )
                            }
                        }
                    }
                }
            }

            val overview = async {
                movieDetailsRepository.getMovieDetails(movieId = movieId).collectLatest { result ->
                    when (result) {
                        is Result.Error -> {
                            _movieScreenUiState.update { state ->
                                state.copy(
                                    loading = false,
                                    overViewError = result.message
                                )
                            }
                        }
                        is Result.Success -> {
                            _movieScreenUiState.update { state ->
                                state.copy(
                                    loading = false,
                                    overViewError = null,
                                    movieDetails = result.data
                                )
                            }
                        }
                    }
                }
            }
            related.await()
            credit.await()
            overview.await()
        }
    }
}

data class MovieScreenUiState(
    val loading: Boolean = false,
    val movieDetails: SingleMovieResponseDto? = null,
    val cast: List<Cast> = emptyList(),
    val relatedMovies: List<Movie> = emptyList(),
    val overViewError: String? = null,
    val castError: String? = null,
    val relatedMoviesError: String? = null
)