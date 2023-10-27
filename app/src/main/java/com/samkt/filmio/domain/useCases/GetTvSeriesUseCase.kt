package com.samkt.filmio.domain.useCases

data class GetTvSeriesUseCase(
    val getPopularTvSeries: GetPopularTvSeries,
    val getLatestTvSeries: GetLatestTvSeries,
    val getTrendingTvSeries:GetTrendingTvSeries
)