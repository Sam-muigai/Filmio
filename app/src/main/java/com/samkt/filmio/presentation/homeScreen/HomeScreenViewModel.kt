package com.samkt.filmio.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.domain.repository.GetMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: GetMoviesRepository,
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    private val _trendingMovies = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    private val _trendingTvSeries = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    private val _upcomingMovies = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    private val _topRatedMovies = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    val popularMovies: StateFlow<PagingData<Result>>
        get() = _popularMovies

    val trendingMovies: StateFlow<PagingData<Result>>
        get() = _trendingMovies

    val trendingTvSeries: StateFlow<PagingData<Result>>
        get() = _trendingTvSeries

    val upcomingMovies: StateFlow<PagingData<Result>>
        get() = _upcomingMovies

    val topRatedMovies: StateFlow<PagingData<Result>>
        get() = _topRatedMovies

    init {
        getMovies()
    }

    fun getMovies() {
        getPopularMovies()
        getTrendingMovies()
        getTrendingTvSeries()
        getUpcomingMovies()
        getTopRatedMovies()
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            repository.getTopRatedMovies().cachedIn(viewModelScope).collect {
                _topRatedMovies.value = it
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            repository.getUpcomingMovies().cachedIn(viewModelScope).collect {
                _upcomingMovies.value = it
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies().cachedIn(viewModelScope).collect {
                _popularMovies.value = it
            }
        }
    }

    private fun getTrendingTvSeries() {
        viewModelScope.launch {
            repository.getPopularTvSeries().cachedIn(viewModelScope).collect {
                _trendingTvSeries.value = it
            }
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            repository.getTrendingMovies().cachedIn(viewModelScope).collect {
                _trendingMovies.value = it
            }
        }
    }
}
