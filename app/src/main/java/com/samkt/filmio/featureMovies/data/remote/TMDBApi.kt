package com.samkt.filmio.featureMovies.data.remote

import com.samkt.filmio.featureMovies.data.remote.dtos.MoviesResponseDto
import com.samkt.filmio.featureMovies.data.remote.dtos.TvSeriesResponseDto
import com.samkt.filmio.featureMovies.data.remote.dtos.credits.CreditResponseDto
import com.samkt.filmio.featureMovies.data.remote.dtos.searchResponse.SearchResponseDto
import com.samkt.filmio.featureMovies.data.remote.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.featureMovies.data.remote.dtos.singleTvSeries.SingleTvSeriesResponse
import com.samkt.filmio.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US"
    ): MoviesResponseDto

    @GET("trending/all/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US"
    ): MoviesResponseDto

    @GET("trending/tv/day")
    suspend fun getTrendingTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US"
    ): TvSeriesResponseDto

    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US"
    ): TvSeriesResponseDto

    @GET("tv/on_the_air")
    suspend fun getLatestTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US"
    ): TvSeriesResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US"
    ): MoviesResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US"
    ): MoviesResponseDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): SingleMovieResponseDto

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CreditResponseDto

    @GET("movie/{movieId}/similar")
    suspend fun getRelatedMovies(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MoviesResponseDto

    @GET("search/multi")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") searchQuery: String,
        @Query("language") language: String = "en-US"
    ): SearchResponseDto

    @GET("tv/{tv_id}")
    suspend fun getTvSeriesDetails(
        @Path("tv_id") tvSeriesId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): SingleTvSeriesResponse

    @GET("tv/{tvId}/credits")
    suspend fun getTvSeriesCast(
        @Path("tvId") tvSeriesId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CreditResponseDto

    @GET("tv/{tvId}/similar")
    suspend fun getRelatedTvShows(
        @Path("tvId") tvSeriesId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): TvSeriesResponseDto
}
