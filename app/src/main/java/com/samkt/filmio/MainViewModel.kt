package com.samkt.filmio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.filmio.feature_settings.domain.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel(){
    val isDarkTheme: StateFlow<Boolean?> = settingsRepository.isDarkTheme.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )
}