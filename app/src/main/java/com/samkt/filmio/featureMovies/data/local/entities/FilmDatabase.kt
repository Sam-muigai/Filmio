package com.samkt.filmio.featureMovies.data.local.entities

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, TvSeriesEntity::class],
    version = 2
)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun dao(): FilmsDao
}
