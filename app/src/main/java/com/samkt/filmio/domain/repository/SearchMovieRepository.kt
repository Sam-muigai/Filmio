package com.samkt.filmio.domain.repository

import androidx.paging.PagingData
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.data.dtos.searchResponse.SearchResult
import com.samkt.filmio.util.Result
import kotlinx.coroutines.flow.Flow


interface SearchMovieRepository {

    suspend fun searchMovie(searchQuery:String): Flow<Result<List<SearchResult>>>

}