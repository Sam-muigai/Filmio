package com.samkt.filmio.data.repository

import com.samkt.filmio.data.TMDBApi
import com.samkt.filmio.data.dtos.TvSeriesResponseDto
import com.samkt.filmio.data.dtos.credits.CreditResponseDto
import com.samkt.filmio.data.dtos.singleTvSeries.SingleTvSeriesResponse
import com.samkt.filmio.domain.repository.GetTvSeriesDetailsRepository
import com.samkt.filmio.util.Result
import com.samkt.filmio.util.safeApiCall
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetTvSeriesDetailRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : GetTvSeriesDetailsRepository {
    override suspend fun getTvSeriesDetailsRepository(tvSeriesId: Int):
        Flow<Result<SingleTvSeriesResponse>> {
        return flowOf(safeApiCall { tmdbApi.getTvSeriesDetails(tvSeriesId = tvSeriesId) })
    }

    override suspend fun getTvSeriesCredits(tvSeriesId: Int):
        Flow<Result<CreditResponseDto>> {
        return flowOf(safeApiCall { tmdbApi.getTvSeriesCast(tvSeriesId) })
    }

    override suspend fun getRelatedTvSeries(tvSeriesId: Int):
        Flow<Result<TvSeriesResponseDto>> {
        return flowOf(safeApiCall { tmdbApi.getRelatedTvShows(tvSeriesId) })
    }
}
