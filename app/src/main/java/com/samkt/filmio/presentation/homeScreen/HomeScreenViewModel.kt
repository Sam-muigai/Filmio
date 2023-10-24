package com.samkt.filmio.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.domain.repository.GetMoviesRepository
import com.samkt.filmio.domain.repository.GetTvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val moviesRepository: GetMoviesRepository,
    private val tvSeriesRepository: GetTvSeriesRepository
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    private val _trendingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    private val _trendingTvSeries = MutableStateFlow<PagingData<TVSeries>>(PagingData.empty())
    private val _upcomingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    private val _topRatedMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val popularMovies: StateFlow<PagingData<Movie>>
        get() = _popularMovies

    val trendingMovies: StateFlow<PagingData<Movie>>
        get() = _trendingMovies

    val trendingTvSeries: StateFlow<PagingData<TVSeries>>
        get() = _trendingTvSeries

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
            moviesRepository.getTopRatedMovies().cachedIn(viewModelScope).collect {
                _topRatedMovies.value = it
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            moviesRepository.getUpcomingMovies().cachedIn(viewModelScope).collect {
                _upcomingMovies.value = it
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            moviesRepository.getPopularMovies().cachedIn(viewModelScope).collect {
                _popularMovies.value = it
            }
        }
    }

    private fun getPopularTvSeries() {
        viewModelScope.launch {
            tvSeriesRepository.getPopularTvSeries().cachedIn(viewModelScope).collect {
                _trendingTvSeries.value = it
            }
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            moviesRepository.getTrendingMovies().cachedIn(viewModelScope).collect {
                _trendingMovies.value = it
            }
        }
    }
}
