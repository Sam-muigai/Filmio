package com.samkt.filmio.featureSettings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.samkt.filmio.featureSettings.domain.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepositoryImpl @Inject constructor(
    context: Context
) : SettingsRepository {

    private val dataStore = context.settingsDataStore

    override val isDarkTheme: Flow<Boolean?>
        get() = dataStore.data.map { preference ->
            preference[KEY_IS_DARK_THEME]

        }

    override suspend fun saveDarkTheme(isDarkTheme: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_IS_DARK_THEME] = isDarkTheme
        }
    }

    companion object {
        private val KEY_IS_DARK_THEME = booleanPreferencesKey("key_is_dark_theme")
    }
}
