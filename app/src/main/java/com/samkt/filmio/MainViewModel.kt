package com.samkt.filmio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.filmio.featureSettings.domain.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val isDarkTheme: StateFlow<Boolean?> = settingsRepository.isDarkTheme.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun setTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveDarkTheme(isDarkTheme)
        }
    }
}
