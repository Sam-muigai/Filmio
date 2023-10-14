package com.samkt.filmio.data

import com.samkt.filmio.data.dtos.MoviesResponseDto
import com.samkt.filmio.data.dtos.TvSeriesResponseDto
import com.samkt.filmio.data.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ): MoviesResponseDto

    @GET("trending/all/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ): MoviesResponseDto

    @GET("trending/tv/day")
    suspend fun getTrendingTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ): TvSeriesResponseDto
    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ):TvSeriesResponseDto


    @GET("tv/on_the_air")
    suspend fun getLatestTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ):TvSeriesResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ): MoviesResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ): MoviesResponseDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId:Int,
        @Query("api_key") apiKey: String = API_KEY,
    ):SingleMovieResponseDto

}
