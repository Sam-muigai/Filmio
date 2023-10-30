package com.samkt.filmio.presentation.tvSeriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.domain.useCases.GetTvSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel @Inject constructor(
    private val getTvSeriesUseCase: GetTvSeriesUseCase
) : ViewModel() {

    private val _popularTvSeries = MutableStateFlow<PagingData<TVSeries>>(PagingData.empty())
    private val _trendingTvSeries = MutableStateFlow<PagingData<TVSeries>>(PagingData.empty())
    private val _latestTvSeries = MutableStateFlow<PagingData<TVSeries>>(PagingData.empty())
    val popularTvSeries: StateFlow<PagingData<TVSeries>>
        get() = _popularTvSeries

    val trendingTvSeries: StateFlow<PagingData<TVSeries>>
        get() = _trendingTvSeries

    val latestTvSeries: StateFlow<PagingData<TVSeries>>
        get() = _latestTvSeries

    init {
        getTvSeries()
    }

    private fun getTvSeries() {
        getTrendingTvSeries()
        getPopularTvSeries()
        getLatestTvSeries()
    }

    private fun getLatestTvSeries() {
        viewModelScope.launch {
            getTvSeriesUseCase.getLatestTvSeries(this).collect {
                _latestTvSeries.value = it
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

    private fun getTrendingTvSeries() {
        viewModelScope.launch {
            getTvSeriesUseCase.getTrendingTvSeries(this).collect {
                _trendingTvSeries.value = it
            }
        }
    }
}
