package com.samkt.filmio.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_entity"
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val title: String?
)
