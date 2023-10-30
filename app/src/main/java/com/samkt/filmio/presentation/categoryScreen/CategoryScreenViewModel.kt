package com.samkt.filmio.presentation.categoryScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.domain.useCases.GetMoviesUseCase
import com.samkt.filmio.domain.useCases.GetTvSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val getTvSeriesUseCase: GetTvSeriesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())

    private val _tvSeries = MutableStateFlow<PagingData<TVSeries>>(PagingData.empty())

    var isMovie by mutableStateOf(true)
        private set

    fun setIsMovie(value: Boolean) {
        isMovie = value
    }

    val movies: StateFlow<PagingData<Movie>>
        get() = _movies
    val tvSeries: StateFlow<PagingData<TVSeries>>
        get() = _tvSeries

    init {
        val category = savedStateHandle.get<String>("category") ?: "Popular"
        getFilms(category)
    }

    private fun getFilms(
        category: String,
        isMovie: Boolean = true
    ) {
        getMovies(category)
        getTvSeries(category)
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

    private fun getTvSeries(category: String) {
        viewModelScope.launch {
            getTvSeriesUseCase.run {
                when (category) {
                    "Popular" -> getPopularTvSeries(this@launch)
                    "Trending" -> getTrendingTvSeries(this@launch)
                    "Upcoming" -> getLatestTvSeries(this@launch)
                    else -> getLatestTvSeries(this@launch)
                }
            }.collect {
                _tvSeries.value = it
            }
        }
    }
}
