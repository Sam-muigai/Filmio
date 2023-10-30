package com.samkt.filmio.domain.repository

import com.samkt.filmio.data.dtos.TvSeriesResponseDto
import com.samkt.filmio.data.dtos.credits.CreditResponseDto
import com.samkt.filmio.data.dtos.singleTvSeries.SingleTvSeriesResponse
import com.samkt.filmio.util.Result
import kotlinx.coroutines.flow.Flow

interface GetTvSeriesDetailsRepository {

    suspend fun getTvSeriesDetailsRepository(tvSeriesId: Int): Flow<Result<SingleTvSeriesResponse>>
    suspend fun getTvSeriesCredits(tvSeriesId: Int): Flow<Result<CreditResponseDto>>

    suspend fun getRelatedTvSeries(tvSeriesId: Int): Flow<Result<TvSeriesResponseDto>>
}
