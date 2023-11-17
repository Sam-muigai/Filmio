package com.samkt.filmio.featureMovies.domain.useCases

data class GetTvSeriesUseCase(
    val getPopularTvSeries: GetPopularTvSeries,
    val getLatestTvSeries: GetLatestTvSeries,
    val getTrendingTvSeries: GetTrendingTvSeries
)
