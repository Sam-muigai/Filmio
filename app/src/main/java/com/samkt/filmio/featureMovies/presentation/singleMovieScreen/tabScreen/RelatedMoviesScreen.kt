package com.samkt.filmio.featureMovies.presentation.singleMovieScreen.tabScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.samkt.filmio.featureMovies.presentation.sharedComponents.MovieItem
import com.samkt.filmio.featureMovies.presentation.singleMovieScreen.MovieScreenUiState

@Composable
fun RelatedMoviesScreen(
    uiState: MovieScreenUiState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 1000.dp),
        content = {
            items(uiState.relatedMovies) { movie ->
                val filmName = movie.title ?: movie.originalTitle ?: "Unknown"
                MovieItem(
                    imageUrl = movie.posterPath ?: movie.backdropPath ?: "",
                    filmName = filmName,
                    filmOverview = movie.overview ?: ""
                )
            }
        }
    )
}
