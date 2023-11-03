package com.samkt.filmio.data.mappers

import com.samkt.filmio.data.local.entities.MovieEntity
import com.samkt.filmio.data.local.entities.TvSeriesEntity
import com.samkt.filmio.data.remote.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.data.remote.dtos.singleTvSeries.SingleTvSeriesResponse

fun SingleMovieResponseDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id,
        originalTitle,
        overview,
        posterPath,
        title
    )
}

fun SingleTvSeriesResponse.toTvSeriesEntity(): TvSeriesEntity {
    return TvSeriesEntity(
        id,
        originalName,
        overview,
        posterPath,
        name
    )
}
