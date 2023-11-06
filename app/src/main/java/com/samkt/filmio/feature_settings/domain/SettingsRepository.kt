package com.samkt.filmio.feature_settings.domain

import kotlinx.coroutines.flow.Flow


interface SettingsRepository {

    val isDarkTheme: Flow<Boolean?>
    suspend fun saveDarkTheme(isDarkTheme:Boolean)
}