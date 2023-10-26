package com.samkt.filmio.presentation.singleMovieScreen.tabScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.presentation.sharedComponents.MovieCard
import com.samkt.filmio.presentation.sharedComponents.MovieItem
import com.samkt.filmio.presentation.singleMovieScreen.MovieScreenUiState

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


