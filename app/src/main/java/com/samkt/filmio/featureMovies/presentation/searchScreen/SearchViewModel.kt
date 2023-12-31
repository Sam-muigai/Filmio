package com.samkt.filmio.featureMovies.presentation.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.filmio.featureMovies.data.remote.dtos.searchResponse.SearchResult
import com.samkt.filmio.featureMovies.domain.repository.SearchMovieRepository
import com.samkt.filmio.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieRepository: SearchMovieRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _searchScreenUiState = MutableStateFlow(SearchScreenUiState())
    val searchScreenUiState: StateFlow<SearchScreenUiState>
        get() = _searchScreenUiState

    fun searchMovie(searchQuery: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            _searchScreenUiState.update {
                it.copy(
                    loading = true
                )
            }
            searchMovieRepository.searchMovie(searchQuery).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _searchScreenUiState.update {
                            it.copy(
                                loading = false,
                                error = result.message,
                                movies = emptyList()
                            )
                        }
                    }
                    is Result.Success -> {
                        _searchScreenUiState.update {
                            it.copy(
                                loading = false,
                                error = null,
                                movies = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }
}

data class SearchScreenUiState(
    val loading: Boolean = false,
    val movies: List<SearchResult> = emptyList(),
    val error: String? = null
)
