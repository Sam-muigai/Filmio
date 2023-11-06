package com.samkt.filmio.featureSettings.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val isDarkTheme: Flow<Boolean?>
    suspend fun saveDarkTheme(isDarkTheme: Boolean)
}
