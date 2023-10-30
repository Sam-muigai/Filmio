package com.samkt.filmio.presentation.singleTvSeriesScreen.tabScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samkt.filmio.presentation.sharedComponents.MovieItem
import com.samkt.filmio.presentation.singleTvSeriesScreen.TvSeriesUiState

@Composable
fun RelatedTvSeriesScreen(
    uiState: TvSeriesUiState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 1000.dp),
        content = {
            items(uiState.relatedTvSeries) { series ->
                val filmName = series.originalName ?: series.name ?: "Unknown"
                MovieItem(
                    imageUrl = series.posterPath ?: series.backdropPath ?: "",
                    filmName = filmName,
                    filmOverview = series.overview ?: ""
                )
            }
        }
    )
}
