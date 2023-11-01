package com.samkt.filmio.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.samkt.filmio.data.remote.dtos.Movie
import com.samkt.filmio.data.remote.dtos.TVSeries
import com.samkt.filmio.domain.useCases.GetMoviesUseCase
import com.samkt.filmio.domain.useCases.GetTvSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getTvSeriesUseCase: GetTvSeriesUseCase
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    private val _trendingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    private val _popularTvSeries = MutableStateFlow<PagingData<TVSeries>>(PagingData.empty())
    private val _upcomingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    private val _topRatedMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val popularMovies: StateFlow<PagingData<Movie>>
        get() = _popularMovies

    val trendingMovies: StateFlow<PagingData<Movie>>
        get() = _trendingMovies

    val popularTvSeries: StateFlow<PagingData<TVSeries>>
        get() = _popularTvSeries

    val upcomingMovies: StateFlow<PagingData<Movie>>
        get() = _upcomingMovies

    val topRatedMovies: StateFlow<PagingData<Movie>>
        get() = _topRatedMovies

    init {
        getMovies()
    }

    fun getMovies() {
        getPopularMovies()
        getTrendingMovies()
        getPopularTvSeries()
        getUpcomingMovies()
        getTopRatedMovies()
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            getMoviesUseCase.getTopRatedMovies(this).collect {
                _topRatedMovies.value = it
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            getMoviesUseCase.getUpcomingMovies(this).collect {
                _upcomingMovies.value = it
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getMoviesUseCase.getPopularMovies(this).collect {
                _popularMovies.value = it
            }
        }
    }

    private fun getPopularTvSeries() {
        viewModelScope.launch {
            getTvSeriesUseCase.getPopularTvSeries(this).collect {
                _popularTvSeries.value = it
            }
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            getMoviesUseCase.getTrendingMovies(this).collect {
                _trendingMovies.value = it
            }
        }
    }
}
