package com.samkt.filmio.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_series"
)
data class TvSeriesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val originalName: String?,
    val overview: String?,
    val posterPath: String?,
    val name: String?
)
