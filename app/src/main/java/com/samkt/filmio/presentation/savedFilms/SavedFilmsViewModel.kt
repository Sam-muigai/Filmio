package com.samkt.filmio.presentation.savedFilms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.filmio.data.local.entities.MovieEntity
import com.samkt.filmio.data.local.entities.TvSeriesEntity
import com.samkt.filmio.domain.repository.LocalFilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SavedFilmsViewModel @Inject constructor(
    private val localMoviesRepository: LocalFilmsRepository
):ViewModel(){

    private val _savedMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    private val _savedTvSeries = MutableStateFlow<List<TvSeriesEntity>>(emptyList())
    val savedMovies:StateFlow<List<MovieEntity>>
        get() = _savedMovies
    val savedTvSeries:StateFlow<List<TvSeriesEntity>>
        get() = _savedTvSeries

    init {
        getLocalMovies()
        getSavedTvSeries()
    }

    private fun getLocalMovies() {
        localMoviesRepository.getAllMovies().onEach {movies ->
            _savedMovies.update { movies }
        }.launchIn(viewModelScope)
    }
    private fun getSavedTvSeries(){
        localMoviesRepository.getAllTvSeries().onEach{tvSeries ->
            _savedTvSeries.update { tvSeries }
        }.launchIn(viewModelScope)
    }
}