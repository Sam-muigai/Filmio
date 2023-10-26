package com.samkt.filmio.presentation.singleMovieScreen.tabScreen

import androidx.compose.runtime.Composable
import com.samkt.filmio.presentation.singleMovieScreen.MovieScreenUiState
import com.samkt.filmio.presentation.singleTvSeriesScreen.TvSeriesUiState
import com.samkt.filmio.presentation.singleTvSeriesScreen.tabScreen.RelatedTvSeriesScreen
import com.samkt.filmio.presentation.singleTvSeriesScreen.tabScreen.TvSeriesCastScreen
import com.samkt.filmio.presentation.singleTvSeriesScreen.tabScreen.TvSeriesOverviewScreen


typealias MovieTabScreen = @Composable (uiState: MovieScreenUiState) -> Unit
typealias TvSeriesTabScreen = @Composable (uiState: TvSeriesUiState) -> Unit

data class MovieTabsItem(
    val label: String,
    val screen: MovieTabScreen
)

data class TvSeriesTabsItem(
    val label: String,
    val screen: TvSeriesTabScreen
)

val tvSeriesTabs = listOf(
    TvSeriesTabsItem(
        label = "Overview",
        screen = {uiState ->
            TvSeriesOverviewScreen(uiState = uiState)
        }
    ),
    TvSeriesTabsItem(
        label = "Cast",
        screen = {uiState ->
            TvSeriesCastScreen(uiState = uiState)
        }
    ),
    TvSeriesTabsItem(
        label = "Related",
        screen = {uiState ->
            RelatedTvSeriesScreen(uiState = uiState)
        }
    ),
)



val movieTabs = listOf(
    MovieTabsItem(label = "Overview",
        screen = { uiState ->
            MovieOverviewScreen(
                uiState = uiState
            )
        }
    ),
    MovieTabsItem(
        label = "Cast",
        screen = { uiState ->
            MovieCastScreen(
                uiState = uiState
            )
        }
    ),
    MovieTabsItem(label = "Related",
        screen = {uiState ->
            RelatedMoviesScreen(uiState = uiState)
        }
    )
)