package com.samkt.filmio.presentation.singleMovieScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.filmio.data.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.domain.repository.GetMovieDetailsRepository
import com.samkt.filmio.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    private val movieDetailsRepository: GetMovieDetailsRepository
):ViewModel() {

    private val _movieScreenUiState = MutableStateFlow(MovieScreenUiState())
    val movieScreenUiState:StateFlow<MovieScreenUiState>
        get() = _movieScreenUiState.asStateFlow()

    fun getMovieDetails(movieId:Int){
        _movieScreenUiState.update {
            it.copy(
                loading = true
            )
        }
        Timber.d("GetMovieDetails function called")
        viewModelScope.launch {
            movieDetailsRepository.getMovieDetails(movieId = movieId).collectLatest { result ->
                when(result){
                    is Result.Error -> {
                        Timber.d("GetMovieDetails: Error occurred")
                        _movieScreenUiState.update {state ->
                            state.copy(
                                loading = false,
                                error = result.message
                            )
                        }
                    }
                    is Result.Success -> {
                        Timber.d("GetMovieDetails : Successfully retrieved details")
                        _movieScreenUiState.update {state ->
                            state.copy(
                                loading = false,
                                error = null,
                                movieDetails = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}

data class MovieScreenUiState(
    val loading:Boolean = false,
    val movieDetails:SingleMovieResponseDto? = null,
    val error:String? = null
)