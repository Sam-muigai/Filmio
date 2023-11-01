package com.samkt.filmio.presentation.filteredFilmScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.samkt.filmio.data.remote.dtos.Movie
import com.samkt.filmio.data.remote.dtos.TVSeries
import com.samkt.filmio.domain.useCases.GetMoviesUseCase
import com.samkt.filmio.domain.useCases.GetTvSeriesUseCase
import com.samkt.filmio.util.toGenreInt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FilterFilmsScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getTvSeriesUseCase: GetTvSeriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>>
        get() = _movies

    private val _tvSeries = MutableStateFlow<PagingData<TVSeries>>(PagingData.empty())
    val tvSeries: StateFlow<PagingData<TVSeries>>
        get() = _tvSeries

    var type by mutableStateOf("")
        private set
    var category by mutableStateOf("")
        private set

    var genre by mutableStateOf("")
        private set

    init {
        type = savedStateHandle.get<String>("type") ?: "Movies"
        category = savedStateHandle.get<String>("category") ?: "Popular"
        genre = savedStateHandle.get<String>("genre") ?: "Action"
        getFilms(
            type = type,
            category = category,
            genreId = genre.toGenreInt()
        )
    }

    private fun getFilms(
        type: String,
        category: String,
        genreId: Int
    ) {
        if (type == "Movies") {
            getMovies(
                category,
                genreId
            )
        } else {
            getTvSeries(
                category,
                genreId
            )
        }
    }

    private fun getTvSeries(
        category: String,
        genreId: Int
    ) {
        viewModelScope.launch {
            getTvSeriesUseCase.run {
                when (category) {
                    "Popular" -> getPopularTvSeries(this@launch, genreId)
                    "Trending" -> getTrendingTvSeries(this@launch, genreId)
                    "Upcoming" -> getLatestTvSeries(this@launch, genreId)
                    else -> getLatestTvSeries(this@launch, genreId)
                }
            }.collect {
                _tvSeries.value = it
            }
        }
    }

    private fun getMovies(
        category: String,
        genreId: Int
    ) {
        viewModelScope.launch {
            getMoviesUseCase.run {
                when (category) {
                    "Popular" -> getPopularMovies(this@launch, genreId)
                    "Trending" -> getTrendingMovies(this@launch, genreId)
                    "Upcoming" -> getUpcomingMovies(this@launch, genreId)
                    else -> getTopRatedMovies(this@launch, genreId)
                }
            }.collect {
                _movies.value = it
            }
        }
    }
}
