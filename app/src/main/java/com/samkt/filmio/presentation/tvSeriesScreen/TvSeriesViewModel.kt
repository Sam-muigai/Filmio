package com.samkt.filmio.presentation.tvSeriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.domain.repository.GetTvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel @Inject constructor(
    private val tvSeriesRepository: GetTvSeriesRepository
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
            tvSeriesRepository.getLatestTvSeries().cachedIn(viewModelScope).collectLatest {
                _latestTvSeries.value = it
            }
        }
    }

    private fun getPopularTvSeries() {
        viewModelScope.launch {
            tvSeriesRepository.getPopularTvSeries().cachedIn(viewModelScope).collectLatest {
                _popularTvSeries.value = it
            }
        }
    }

    private fun getTrendingTvSeries() {
        viewModelScope.launch {
            tvSeriesRepository.getTrendingTvSeries().cachedIn(viewModelScope).collectLatest {
                _trendingTvSeries.value = it
            }
        }
    }

}