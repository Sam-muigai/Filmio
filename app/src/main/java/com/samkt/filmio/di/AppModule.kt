package com.samkt.filmio.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samkt.filmio.featureMovies.data.local.entities.FilmDatabase
import com.samkt.filmio.featureMovies.data.remote.TMDBApi
import com.samkt.filmio.featureSettings.data.SettingsRepositoryImpl
import com.samkt.filmio.featureSettings.domain.SettingsRepository
import com.samkt.filmio.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@OptIn(ExperimentalSerializationApi::class)
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTMDBApi(): TMDBApi {
        val contentType = "application/json".toMediaType()

        val json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(TMDBApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFilmDatabase(@ApplicationContext context: Context): FilmDatabase {
        return Room.databaseBuilder(context, FilmDatabase::class.java, "film_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }
}
