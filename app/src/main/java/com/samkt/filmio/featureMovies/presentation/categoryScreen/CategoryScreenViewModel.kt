package com.samkt.filmio.featureMovies.presentation.categoryScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import com.samkt.filmio.featureMovies.domain.useCases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())

    val movies: StateFlow<PagingData<Movie>>
        get() = _movies

    init {
        val category = savedStateHandle.get<String>("category") ?: "Popular"
        getMovies(category)
    }

    private fun getMovies(category: String) {
        viewModelScope.launch {
            getMoviesUseCase.run {
                when (category) {
                    "Popular" -> getPopularMovies(this@launch)
                    "Trending" -> getTrendingMovies(this@launch)
                    "Upcoming" -> getUpcomingMovies(this@launch)
                    else -> getTopRatedMovies(this@launch)
                }
            }.collect {
                _movies.value = it
            }
        }
    }
}
