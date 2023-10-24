package com.samkt.filmio.presentation.singleMovieScreen.tabScreen

import androidx.compose.runtime.Composable
import com.samkt.filmio.presentation.singleMovieScreen.MovieScreenUiState


typealias Screen = @Composable (uiState: MovieScreenUiState) -> Unit

data class TabsItem(
    val label: String,
    val screen: Screen
)


val tabs = listOf(
    TabsItem(label = "Overview",
        screen = { uiState ->
            OverviewScreen(
                uiState = uiState
            )
        }
    ),
    TabsItem(
        label = "Cast",
        screen = { uiState ->
            CastScreen(
                uiState = uiState
            )
        }
    ),
    TabsItem(label = "Related",
        screen = {uiState ->
            RelatedMoviesScreen(uiState = uiState)
        }
    )
)