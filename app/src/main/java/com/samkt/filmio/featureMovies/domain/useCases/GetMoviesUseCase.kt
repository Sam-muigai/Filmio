package com.samkt.filmio.featureMovies.domain.useCases

data class GetMoviesUseCase(
    val getPopularMovies: GetPopularMovies,
    val getTopRatedMovies: GetTopRatedMovies,
    val getTrendingMovies: GetTrendingMovies,
    val getUpcomingMovies: GetUpcomingMovies
)
