package com.samkt.filmio.data

import com.samkt.filmio.data.dtos.Movies
import com.samkt.filmio.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ): Movies

    @GET("trending/all/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en-US",
    ): Movies

}
